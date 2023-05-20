package com.messenger.mode;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileMode extends Mode {

    public static FileMode getFileModeInstace() {
        return new FileMode();
    }

    public String readFileInput(String fileName, String filePath) {
        Path path = Paths.get(filePath);
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resultStringBuilder.toString();
    }

    public void writeFileOutPut(String fileName, String filePath, String output) {
        try {
            File file = new File(filePath);
            if (file.createNewFile()) {
                System.out.println("file created");
            } else {
                System.out.println("file already exists");
            }
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(output);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
