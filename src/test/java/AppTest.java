import com.messenger.App;
import com.messenger.exceptions.ModeNotAvailableException;
import com.messenger.mode.ConsoleMode;
import com.messenger.mode.FileMode;
import com.messenger.template.Parser;
import com.messenger.template.TemplateGenerator;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppTest {

    @Test
    public void chooseModeTestWhenModeConsole() throws InterruptedException {
        String input = "console";
        List<String> fields = List.of("from", "to", "subject", "text");
        TemplateGenerator templateGenerator = mock(TemplateGenerator.class);
        ConsoleMode mode = mock(ConsoleMode.class);
        Parser parser = spy(Parser.class);
        when(templateGenerator.generateTemplate(fields))
                .thenReturn("""
                        from : #{value}
                        to : #{value}
                        subject : #{value}
                        text : #{value}
                        """);
        doNothing().when(mode).writeConsoleOutput("""
                from : #{value}
                to : #{value}
                subject : #{value}
                text : #{value}
                """);
        when(mode.readConsoleInput()).thenReturn("""
                from : #{MamukaKiknadze@gmail.com}
                to : #{GiorgiDolidze@gmail.com}
                subject : #{introduction meeting}
                text : #{Hello, you are invited to introductory meeting of out training}
                """);
        Map<String, String> map = new LinkedHashMap<>();
        map.put("from", "MamukaKiknadze@gmail.com");
        map.put("to", "GiorgiDolidze@gmail.com");
        map.put("subject", "introduction meeting");
        map.put("text", "Hello, you are invited to introductory meeting of out training");
        doReturn(map).when(parser).parseTemplate(eq("""
                from : #{MamukaKiknadze@gmail.com}
                to : #{GiorgiDolidze@gmail.com}
                subject : #{introduction meeting}
                text : #{Hello, you are invited to introductory meeting of out training}
                """), eq(fields));
        doNothing().when(mode).writeConsoleOutput("""
                from : MamukaKiknadze@gmail.com
                to : GiorgiDolidze@gmail.com
                subject : introduction meeting
                text : Hello, you are invited to introductory meeting of out training
                """);
        String expected = """
                from : MamukaKiknadze@gmail.com
                to : GiorgiDolidze@gmail.com
                subject : introduction meeting
                text : Hello, you are invited to introductory meeting of out training
                """;

        Assertions.assertEquals(expected, App.getMainInstance().chooseModeAndGiveAnswer(null, null, input, mode, parser, fields));
    }

    @Test
    public void chooseModeTestWhenModeFile() throws InterruptedException {
        String input = "file";
        List<String> fields = List.of("from", "to", "subject", "text");
        TemplateGenerator templateGenerator = mock(TemplateGenerator.class);
        FileMode modeFile = mock(FileMode.class);
        Parser parser = spy(Parser.class);
        when(templateGenerator.generateTemplate(fields))
                .thenReturn("""
                        from : #{value}
                        to : #{value}
                        subject : #{value}
                        text : #{value}
                        """);
        doNothing().when(modeFile).writeFileOutPut(any(), any(), eq("""
                from : #{value}
                to : #{value}
                subject : #{value}
                text : #{value}
                """));
        ByteArrayInputStream in = new ByteArrayInputStream("yes".getBytes());
        System.setIn(in);
        when(modeFile.readFileInput(any(), eq("/resources/file.txt"))).thenReturn("""
                from : #{MamukaKiknadze@gmail.com}
                to : #{GiorgiDolidze@gmail.com}
                subject : #{introduction meeting}
                text : #{Hello, you are invited to introductory meeting of out training}
                """);
        Map<String, String> map = new LinkedHashMap<>();
        map.put("from", "MamukaKiknadze@gmail.com");
        map.put("to", "GiorgiDolidze@gmail.com");
        map.put("subject", "introduction meeting");
        map.put("text", "Hello, you are invited to introductory meeting of out training");
        doReturn(map).when(parser).parseTemplate(eq("""
                from : #{MamukaKiknadze@gmail.com}
                to : #{GiorgiDolidze@gmail.com}
                subject : #{introduction meeting}
                text : #{Hello, you are invited to introductory meeting of out training}
                """), eq(fields));
        doNothing().when(modeFile).writeFileOutPut(eq("fileOutput"), eq("/resources/fileoutput.txt"),
                eq("""
                        from : MamukaKiknadze@gmail.com
                        to : GiorgiDolidze@gmail.com
                        subject : introduction meeting
                        text : Hello, you are invited to introductory meeting of out training
                        """));

        String expected = """
                from : MamukaKiknadze@gmail.com
                to : GiorgiDolidze@gmail.com
                subject : introduction meeting
                text : Hello, you are invited to introductory meeting of out training
                """;
        Assertions.assertEquals(expected, App.getMainInstance().chooseModeAndGiveAnswer("fileInput", "/resources/file.txt", input, modeFile, parser, fields));
    }

    @Test
    public void chooseUnknownModeTest() {
        String mode = "dummyMode";
        List<String> fields = List.of("from", "to", "subject", "text");
        TemplateGenerator templateGenerator = mock(TemplateGenerator.class);
        ConsoleMode modeConsole = mock(ConsoleMode.class);
        when(templateGenerator.generateTemplate(fields))
                .thenReturn("""
                        from : #{value}
                        to : #{value}
                        subject : #{value}
                        text : #{value}
                        """);
        Assertions.assertThrows(ModeNotAvailableException.class, () -> App.getMainInstance().chooseModeAndGiveAnswer(null, null, mode, modeConsole, null, fields));

    }
}
