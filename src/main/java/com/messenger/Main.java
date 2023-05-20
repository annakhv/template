package com.messenger;

import com.messenger.mode.ConsoleMode;
import com.messenger.mode.Mode;
import com.messenger.template.Parser;
import com.messenger.template.TemplateGenerator;

import java.util.List;
import java.util.Map;

public class Main {

    public static Main getMainInstance() {
        return new Main();
    }

    public static void main(String[] args) {
        System.out.println("Hello world!");
    }


    public String chooseModeAndGiveAnswer(String mode, Mode modeInstance,Parser parser) {
        List<String> fields = List.of("from", "to", "cc", "subject", "text");
        String template = TemplateGenerator.getTemplateGenerator()
                .generateTemplate(fields);
        if ("file".equalsIgnoreCase(mode)) {

        } else if ("console".equalsIgnoreCase(mode)) {
            ConsoleMode consoleMode = (ConsoleMode)modeInstance;
            String inputFromTemplate = consoleMode.readConsoleInput();
            Map<String, String> resultMap = parser.parseTemplate(inputFromTemplate, fields);
            String result=createResult(resultMap);
             consoleMode.writeConsoleOutput(result);
             return result;
        }
        return null;
    }


    private String createResult(Map<String, String> map) {
        String result = "";
        for (Map.Entry entry : map.entrySet()) {
             result+=entry.getKey()+" : "+entry.getValue()+"\n";

        }
        return result;
    }
}