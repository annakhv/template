import com.messenger.Main;
import com.messenger.mode.ConsoleMode;
import com.messenger.mode.FileMode;
import com.messenger.template.Parser;
import com.messenger.template.TemplateGenerator;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MainTest {

    @Test
    public void chooseModeTestWhenModeConsole() throws InterruptedException {
        String input = "console";
        TemplateGenerator templateGenerator = mock(TemplateGenerator.class);
        ConsoleMode mode = mock(ConsoleMode.class);
        Parser parser = spy(Parser.class);
        when(templateGenerator.generateTemplate(List.of("from", "to", "subject", "text")))
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
        doReturn(Map.of("from", "MamukaKiknadze@gmail.com",
                "to", "GiorgiDolidze@gmail.com",
                "subject", "introduction meeting",
                "text", "Hello, you are invited to introductory meeting of out training")).when(parser).parseTemplate(eq("""
                from : #{MamukaKiknadze@gmail.com}
                to : #{GiorgiDolidze@gmail.com}
                subject : #{introduction meeting}
                text : #{Hello, you are invited to introductory meeting of out training}
                """), eq(List.of("from", "to", "subject", "text")));
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

        Assertions.assertEquals(expected, Main.getMainInstance().chooseModeAndGiveAnswer(null, null, input, mode, parser));
    }

    @Test
    public void chooseModeTestWhenModeFile() throws InterruptedException {
        String input = "file";
        TemplateGenerator templateGenerator = mock(TemplateGenerator.class);
        FileMode mode = mock(FileMode.class);
        Parser parser = spy(Parser.class);
        when(templateGenerator.generateTemplate(List.of("from", "to", "subject", "text")))
                .thenReturn("""
                        from : #{value}
                        to : #{value}
                        subject : #{value}
                        text : #{value}
                        """);
        doNothing().when(mode).writeFileOutPut(any(), any(), eq("""
                from : #{value}
                to : #{value}
                subject : #{value}
                text : #{value}
                """));
        when(mode.readFileInput(any(), eq("/resources/file.txt"))).thenReturn("""
                from : #{MamukaKiknadze@gmail.com}
                to : #{GiorgiDolidze@gmail.com}
                subject : #{introduction meeting}
                text : #{Hello, you are invited to introductory meeting of out training}
                """);
        doReturn(Map.of("from", "MamukaKiknadze@gmail.com",
                "to", "GiorgiDolidze@gmail.com",
                "subject", "introduction meeting",
                "text", "Hello, you are invited to introductory meeting of out training")).when(parser).parseTemplate(eq("""
                from : #{MamukaKiknadze@gmail.com}
                to : #{GiorgiDolidze@gmail.com}
                subject : #{introduction meeting}
                text : #{Hello, you are invited to introductory meeting of out training}
                """), eq(List.of("from", "to", "subject", "text")));
        doNothing().when(mode).writeFileOutPut(eq("fileOutput"), eq("/resources/fileoutput.txt"),
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

        Assertions.assertEquals(expected, Main.getMainInstance().chooseModeAndGiveAnswer("fileInput", "/resources/file.txt", input, mode, parser));
    }

    @Test
    public void chooseUnknownModeTest() {
        String mode = "dummyMode";
        TemplateGenerator templateGenerator = mock(TemplateGenerator.class);
        when(templateGenerator.generateTemplate(List.of("from", "to", "subject", "text")))
                .thenReturn("""
                        from : #{value}
                        to : #{value}
                        subject : #{value}
                        text : #{value}
                        """);
        Assertions.assertThrows(RuntimeException.class, () -> Main.getMainInstance().chooseModeAndGiveAnswer(null, null, mode, null, null));

    }
}
