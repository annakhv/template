package com.messenger;

import com.messenger.exceptions.ModeNotAvailableException;
import com.messenger.mode.ConsoleMode;
import com.messenger.mode.FileMode;
import com.messenger.mode.Mode;
import com.messenger.template.Parser;
import com.messenger.template.TemplateGenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

public class App {

    public static App getMainInstance() {
        return new App();
    }


    public String chooseModeAndGiveAnswer(String filename, String filePath, String mode, Mode modeInstance, Parser parser, List<String> fields) {
        System.out.println("-------------------------");
        String template = TemplateGenerator.getTemplateGenerator()
                .generateTemplate(fields);
        if ("file".equalsIgnoreCase(mode)) {
            FileMode fileMode = (FileMode) modeInstance;
            fileMode.writeFileOutPut(filename, filePath, template);
            if (continueExecution()) {
                String inputFromTemplate = fileMode.readFileInput(filename, filePath);
                Map<String, String> resulMap = parser.parseTemplate(inputFromTemplate, fields);
                String result = createResult(resulMap);
                fileMode.writeFileOutPut("fileOutput.txt", "C:\\Users\\annak\\OneDrive\\Desktop\\fileOutput.txt", result);
                return result;
            } else {
                System.out.println("program execution is stopped");
                System.exit(1);
            }
        } else if ("console".equalsIgnoreCase(mode)) {
            ConsoleMode consoleMode = (ConsoleMode) modeInstance;
            consoleMode.writeConsoleOutput(template);
            System.out.println("-------------------------");
            System.out.println("please provide you input in accordance with given template, after entering the input" +
                    " please click ctrl D !!!!");
            System.out.println("-------------------------");
            String inputFromTemplate = consoleMode.readConsoleInput();
            Map<String, String> resultMap = parser.parseTemplate(inputFromTemplate, fields);
            String result = createResult(resultMap);
            consoleMode.writeConsoleOutput(result);
            return result;
        }
        throw new ModeNotAvailableException("this mode " + mode + " is not available");
    }


    private String createResult(Map<String, String> map) {
        String result = "";
        for (Map.Entry entry : map.entrySet()) {
            result += entry.getKey() + " : " + entry.getValue() + "\n";

        }
        return result;
    }

    private boolean continueExecution() {
        System.out.println("please write yes when template file is filled in");
        String consoleInput;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            consoleInput = reader.readLine();
            System.out.println("console input is "+consoleInput);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "yes".equalsIgnoreCase(consoleInput);
    }
}