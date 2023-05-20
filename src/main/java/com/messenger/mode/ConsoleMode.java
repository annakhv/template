package com.messenger.mode;

import java.util.Map;

public class ConsoleMode extends Mode{

    public static ConsoleMode getConsoleModeInstance(){
        return new ConsoleMode();
    }

    public String readConsoleInput(){
        return null;
    }

    public void writeConsoleOutput(String output){
    }
}
