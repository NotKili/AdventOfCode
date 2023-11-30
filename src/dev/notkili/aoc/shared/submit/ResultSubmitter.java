package dev.notkili.aoc.shared.submit;

import dev.notkili.aoc.shared.parse.SessionCookie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class ResultSubmitter {
    private static final String SUBMIT_URL = "https://adventofcode.com/%d/day/%d/answer";

    private final String input;
    private final int year;
    private final int day;
    private final int part;

    public ResultSubmitter(String input, int year, int day, int part) {
        this.input = input;
        this.year = year;
        this.day = day;
        this.part = part;
    }

    public void submit() {
        try {
            HttpURLConnection connection = (HttpURLConnection) getURI().toURL().openConnection();

            connection.setRequestMethod("POST");

            connection.setDoOutput(true);

            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            var cookie = SessionCookie.INSTANCE.getCookie();

            if (cookie.isEmpty()) {
                System.err.println("No cookie found, could not auto-submit solution");
                return;
            }

            connection.setRequestProperty("Cookie", "session=" + cookie.get());

            byte[] postData = getInput().getBytes(StandardCharsets.UTF_8);

            try (OutputStream outputStream = connection.getOutputStream()) {
                outputStream.write(postData);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append('\n');
            }

            evaluateResponse(Jsoup.parse(content.toString()).selectFirst("article").selectFirst("p"));
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void evaluateResponse(Element element) {
        System.err.println(element.text().replaceAll("\\[(.*?)]", "").trim());
    }

    private URI getURI() {
        return URI.create(String.format(SUBMIT_URL, year, day));
    }

    private String getInput() {
        return String.format("level=%d&answer=%s", part, input);
    }
}
