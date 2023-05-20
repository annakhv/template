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
    public void chooseModeTestWhenModeConsole() {
        String input = "console";
        TemplateGenerator templateGenerator = mock(TemplateGenerator.class);
        when(templateGenerator.generateTemplate(List.of("from", "to", "subject", "text")))
                .thenReturn("""
                        from : #{value}
                        to : #{value}
                        subject : #{value}
                        text : #{value}
                        """);
        ConsoleMode mode = mock(ConsoleMode.class);
        when(mode.readConsoleInput()).thenReturn("""
                from : #{MamukaKiknadze@gmail.com}
                to : #{GiorgiDolidze@gmail.com}
                subject : #{introduction meeting}
                text : #{Hello, you are invited to introductory meeting of out training}
                """);
        Parser parser = spy(Parser.class);
        when(parser.parseTemplate("""
                from : #{MamukaKiknadze@gmail.com}
                to : #{GiorgiDolidze@gmail.com}
                subject : #{introduction meeting}
                text : #{Hello, you are invited to introductory meeting of out training}
                """, List.of("from", "to", "subject", "text")))
                .thenReturn(Map.of("from", "MamukaKiknadze@gmail.com",
                        "to", "GiorgiDolidze@gmail.com",
                        "subject", "introduction meeting",
                        "text", "Hello, you are invited to introductory meeting of out training"));
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

        Assertions.assertEquals(expected, Main.getMainInstance().chooseModeAndGiveAnswer(null,null,input, mode, parser));
    }

    @Test
    public void chooseModeTestWhenModeFile() {
        String input = "file";
        TemplateGenerator templateGenerator = mock(TemplateGenerator.class);
        when(templateGenerator.generateTemplate(List.of("from", "to", "subject", "text")))
                .thenReturn("""
                        from : #{value}
                        to : #{value}
                        subject : #{value}
                        text : #{value}
                        """);
        FileMode mode = mock(FileMode.class);
        when(mode.readFileInput("fileInput", "/resources/file.txt")).thenReturn("""
                from : #{MamukaKiknadze@gmail.com}
                to : #{GiorgiDolidze@gmail.com}
                subject : #{introduction meeting}
                text : #{Hello, you are invited to introductory meeting of out training}
                """);
        Parser parser = spy(Parser.class);
        when(parser.parseTemplate("""
                from : #{MamukaKiknadze@gmail.com}
                to : #{GiorgiDolidze@gmail.com}
                subject : #{introduction meeting}
                text : #{Hello, you are invited to introductory meeting of out training}
                """, List.of("from", "to", "subject", "text"))).thenReturn(Map.of("from", "MamukaKiknadze@gmail.com",
                "to", "GiorgiDolidze@gmail.com",
                "subject", "introduction meeting",
                "text", "Hello, you are invited to introductory meeting of out training"));
        doNothing().when(mode).writeFileOutPut("fileOutput", "/resources/fileoutput.txt",
                """
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

        Assertions.assertEquals(expected, Main.getMainInstance().chooseModeAndGiveAnswer("fileInput", "/resources/file.txt", input, mode, parser));
    }
}
