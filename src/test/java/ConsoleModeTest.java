import com.messenger.mode.ConsoleMode;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ConsoleModeTest {

    @Test
    public void checkConsoleWriteTest() {
        String inputAndOutput = """
                from : #{value}
                to : #{value}
                subject : #{value}
                text : #{value}
                """;
       ConsoleMode consoleMode = ConsoleMode.getConsoleModeInstance();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        consoleMode.writeConsoleOutput(inputAndOutput);
        Assertions.assertEquals(inputAndOutput, outContent.toString());


    }
    @Test
    public void readConsoleInputTest(){
        String inputAndOutput = """
                from : #{value}
                to : #{value}
                subject : #{value}
                text : #{value}
                """;
        ConsoleMode consoleMode = ConsoleMode.getConsoleModeInstance();
        ByteArrayInputStream in = new ByteArrayInputStream(inputAndOutput.getBytes());
        System.setIn(in);
        String result=consoleMode.readConsoleInput();
        Assertions.assertEquals(inputAndOutput,result);

    }

}
