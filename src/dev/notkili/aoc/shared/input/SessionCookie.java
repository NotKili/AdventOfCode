package dev.notkili.aoc.shared.input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Optional;

public class SessionCookie {
    public static final SessionCookie INSTANCE = new SessionCookie();

    private String cookie = null;

    private SessionCookie() {
    }

    public Optional<String> getCookie() {
        if (cookie == null)
            cookie = parseCookie();

        return Optional.ofNullable(cookie);
    }

    private String parseCookie() {
        File cookieFile = new File(new File("").getAbsolutePath(),"cookie.txt");

        if (!cookieFile.exists()) {
            try {
                cookieFile.createNewFile();
                System.err.println("Did not find session cookie file at " + cookieFile.getAbsolutePath() + ", created new one. Please insert your cookie there");
            } catch (Exception e) {
                System.err.println("Unable to create cookie file at " + cookieFile.getAbsolutePath() + ", please create it manually");
                e.printStackTrace();
            }
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(cookieFile)))) {
            return reader.readLine();
        } catch (Exception e) {
            System.err.println("Unable to read cookie file at " + cookieFile.getAbsolutePath());
            e.printStackTrace();
            return null;
        }
    }
}
