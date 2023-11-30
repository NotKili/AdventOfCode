package dev.notkili.aoc.shared.parse;

import dev.notkili.aoc.shared.input.StringInput;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Optional;

public class InputParser {

    private static final String INPUT_URL = "https://adventofcode.com/%d/day/%d/input";

    private final int year;
    private final int day;

    public InputParser(int year, int day) {
        this.year = year;
        this.day = day;
    }

    public Optional<StringInput> getInput() {
        return readFromFile().or(this::readFromWebsite);
    }

    private Optional<StringInput> readFromWebsite() {
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

            content.deleteCharAt(content.length() - 1);

            var input = new StringInput(content.toString());
            writeToFile(input.asString());
            return Optional.of(input);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private Optional<StringInput> readFromFile() {
        return getInputFile(false).map(file -> {
            try (var reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
                var builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line).append('\n');
                }
                return new StringInput(builder.toString());
            } catch (Exception e) {
                System.err.println("Unable to read input for " + year + "/" + day + " from input file");
                e.printStackTrace();
                return null;
            }
        });
    }

    private void writeToFile(String input) {
        getInputFile(true).ifPresentOrElse(file -> {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(input);
            } catch (Exception e) {
                System.err.println("Unable to persist input for " + year + "/" + day + " to input file");
                e.printStackTrace();
            }
        }, () -> System.err.println("Unable to persist input for " + year + "/" + day + " to input file"));
    }

    private Optional<File> getInputFolder() {
        var file = new File(new File("").getAbsolutePath(), "input" + File.separator + year);

        if (!file.exists()) {
            if (!file.mkdirs()) {
                System.err.println("Unable to create input folder at " + file.getAbsolutePath());
                return Optional.empty();
            }
        }

        return Optional.of(file);
    }

    private Optional<File> getInputFile(boolean createIfNotExists) {
        return getInputFolder().map(folder -> {
            var file = new File(folder.getAbsolutePath(), day + ".txt");

            if (!file.exists()) {
                if (!createIfNotExists) {
                    return null;
                }

                try {
                    file.createNewFile();
                } catch (Exception e) {
                    System.err.println("Unable to create input file at " + file.getAbsolutePath());
                    e.printStackTrace();
                    return null;
                }
            }

            return file;
        });
    }

    private String getURL() {
        return String.format(INPUT_URL, year, day);
    }
}
