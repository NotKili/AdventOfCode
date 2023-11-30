package dev.notkili.aoc.shared.parse;

import dev.notkili.aoc.shared.input.StringInput;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Optional;

public class InputParser {

    private static final String INPUT_URL = "https://adventofcode.com/%d/day/%d/input";

    private int year;
    private int day;

    public InputParser(int year, int day) {
        this.year = year;
        this.day = day;
    }

    public Optional<StringInput> getInput() {
        try {
            HttpURLConnection connection = (HttpURLConnection) URI.create(getURL()).toURL().openConnection();

            connection.setRequestMethod("GET");

            var cookie = SessionCookie.INSTANCE.getCookie();

            if (cookie.isEmpty()) {
                return Optional.empty();
            }

            connection.setRequestProperty("Cookie", "session=" + cookie.get());

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append('\n');
            }

            reader.close();
            connection.disconnect();

            String result = content.toString();
            System.out.println(result);
            return Optional.of(new StringInput(result));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private String getURL() {
        return String.format(INPUT_URL, year, day);
    }
}
