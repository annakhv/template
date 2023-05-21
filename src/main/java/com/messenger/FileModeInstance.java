package com.messenger;

import com.messenger.mode.ConsoleMode;
import com.messenger.mode.FileMode;
import com.messenger.mode.Mode;
import com.messenger.template.Parser;

import java.util.List;

public class FileModeInstance {

    public static void main(String[] args){
        App main = App.getMainInstance();
        Mode modeConsole = ConsoleMode.getConsoleModeInstance();
        Mode modeFile = FileMode.getFileModeInstace();
        Parser parser = Parser.getParserInstance();
        List<String> fields = List.of("from", "to", "cc", "subject", "text");
        main.chooseModeAndGiveAnswer("myFile.txt", "C:\\Users\\annak\\OneDrive\\Desktop\\myFile.txt", "file", modeFile, parser, fields);
    }
}
