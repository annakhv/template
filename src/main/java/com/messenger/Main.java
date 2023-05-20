package com.messenger;

import com.messenger.mode.ConsoleMode;
import com.messenger.mode.FileMode;
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
        Main main=Main.getMainInstance();
        Mode mode=ConsoleMode.getConsoleModeInstance();
        Parser parser=Parser.getParserInstance();
        main.chooseModeAndGiveAnswer(null,null,"console",mode,parser);
    }


    public String chooseModeAndGiveAnswer(String filename,String filePath, String mode, Mode modeInstance,Parser parser)  {
        List<String> fields = List.of("from", "to", "cc", "subject", "text");
        String template = TemplateGenerator.getTemplateGenerator()
                .generateTemplate(fields);
        if ("file".equalsIgnoreCase(mode)) {
            FileMode fileMode=(FileMode) modeInstance;
            fileMode.writeFileOutPut(filename,filePath,template);
            String inputFromTemplate=fileMode.readFileInput(filename,filePath);
            System.out.println("template "+inputFromTemplate);
           Map<String,String> resulMap=parser.parseTemplate(inputFromTemplate,fields);
           String result=createResult(resulMap);
           fileMode.writeFileOutPut("fileOutput","resources/fileOutput.txt",result);
           return result;

        } else if ("console".equalsIgnoreCase(mode)) {
            ConsoleMode consoleMode = (ConsoleMode)modeInstance;
            consoleMode.writeConsoleOutput(template);
            String inputFromTemplate = consoleMode.readConsoleInput();
            Map<String, String> resultMap = parser.parseTemplate(inputFromTemplate, fields);
            String result=createResult(resultMap);
             consoleMode.writeConsoleOutput(result);
             return result;
        }
        throw new RuntimeException("this mode "+mode+" is not available");
    }


    private String createResult(Map<String, String> map) {
        String result = "";
        for (Map.Entry entry : map.entrySet()) {
             result+=entry.getKey()+" : "+entry.getValue()+"\n";

        }
        return result;
    }
}