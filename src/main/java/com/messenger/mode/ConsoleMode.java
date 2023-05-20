package com.messenger.mode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleMode extends Mode {

    public static ConsoleMode getConsoleModeInstance() {
        return new ConsoleMode();
    }

    public String readConsoleInput() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in));

            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stringBuilder.toString();
    }

    public void writeConsoleOutput(String output) {
        System.out.print(output);
    }
}
