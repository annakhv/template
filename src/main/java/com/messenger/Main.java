package com.messenger;

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

public class Main {

    public static Main getMainInstance() {
        return new Main();
    }

    public static void main(String[] args) {
        Main main = Main.getMainInstance();
        Mode modeConsole = ConsoleMode.getConsoleModeInstance();
        Mode modeFile = FileMode.getFileModeInstace();
        Parser parser = Parser.getParserInstance();
        List<String> fields = List.of("from", "to", "cc", "subject", "text");
        // main.chooseModeAndGiveAnswer(null,null,"console",modeConsole,parser);
        main.chooseModeAndGiveAnswer("myFile.txt", "src\\main\\resources\\myFile.txt", "file", modeFile, parser, fields);
    }


    public String chooseModeAndGiveAnswer(String filename, String filePath, String mode, Mode modeInstance, Parser parser, List<String> fields) {
        String template = TemplateGenerator.getTemplateGenerator()
                .generateTemplate(fields);
        if ("file".equalsIgnoreCase(mode)) {
            FileMode fileMode = (FileMode) modeInstance;
            fileMode.writeFileOutPut(filename, filePath, template);
            if (continueExecution()) {
                String inputFromTemplate = fileMode.readFileInput(filename, filePath);
                Map<String, String> resulMap = parser.parseTemplate(inputFromTemplate, fields);
                String result = createResult(resulMap);
                fileMode.writeFileOutPut("fileOutput", "resources/fileOutput.txt", result);
                return result;
            } else {
                System.out.println("program execution is stopped");
            }


        } else if ("console".equalsIgnoreCase(mode)) {
            ConsoleMode consoleMode = (ConsoleMode) modeInstance;
            consoleMode.writeConsoleOutput(template);
            String inputFromTemplate = consoleMode.readConsoleInput();
            Map<String, String> resultMap = parser.parseTemplate(inputFromTemplate, fields);
            String result = createResult(resultMap);
            consoleMode.writeConsoleOutput(result);
            return result;
        }
        throw new RuntimeException("this mode " + mode + " is not available");
    }


    private String createResult(Map<String, String> map) {
        String result = "";
        for (Map.Entry entry : map.entrySet()) {
            result += entry.getKey() + " : " + entry.getValue() + "\n";

        }
        return result;
    }

    private boolean continueExecution() {
        System.out.println("please write yes and than click ctrl D when template file is filled in");
        String consoleInput;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            consoleInput = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "yes".equalsIgnoreCase(consoleInput);
    }
}