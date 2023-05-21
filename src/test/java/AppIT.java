import com.messenger.App;
import com.messenger.exceptions.TemplateFieldsEmptyException;
import com.messenger.mode.ConsoleMode;
import com.messenger.mode.FileMode;
import com.messenger.template.Parser;
import com.messenger.template.TemplateGenerator;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppIT {

    @TempDir
    File tempDirectory;
    @Test
    public void chooseModeTestWhenModeConsole() throws InterruptedException {
        String input = "console";
        List<String> fields = List.of("from", "to", "subject", "text");
        ConsoleMode mode = spy(ConsoleMode.class);
        Parser parser = spy(Parser.class);
        String consoleOutput = """
                from : #{MamukaKiknadze@gmail.com}
                to : #{GiorgiDolidze@gmail.com}
                subject : #{introduction meeting}
                text : #{Hello, you are invited to introductory meeting of out training}
                """;
        ByteArrayInputStream in = new ByteArrayInputStream(consoleOutput.getBytes());
        System.setIn(in);
        Map<String, String> map = new LinkedHashMap<>();
        String expected = """
                from : MamukaKiknadze@gmail.com
                to : GiorgiDolidze@gmail.com
                subject : introduction meeting
                text : Hello, you are invited to introductory meeting of out training
                """;
        Assertions.assertEquals(expected, App.getMainInstance().chooseModeAndGiveAnswer(null, null, input, mode, parser, fields));
    }

    @Test
    public void chooseModeTestWhenModeFileWithInvalidFileInput() throws InterruptedException {
        String input = "file";
        List<String> fields = List.of("from", "to", "subject", "text");
        TemplateGenerator templateGenerator = spy(TemplateGenerator.class);
        FileMode modeFile = spy(FileMode.class);
        Parser parser = spy(Parser.class);
        File template = new File(tempDirectory, "template.txt");
        ByteArrayInputStream in = new ByteArrayInputStream("yes".getBytes());
        System.setIn(in);
        Assertions.assertThrows(TemplateFieldsEmptyException.class, ()->App.getMainInstance().chooseModeAndGiveAnswer(template.getName(), template.getPath(), input, modeFile, parser, fields));
    }


    @Test
    public void chooseModeTestWhenModeFileWithvalidFileInput() throws InterruptedException {
        String input = "file";
        List<String> fields = List.of("from", "to", "subject", "text");
        FileMode modeFile = mock(FileMode.class);
        Parser parser = spy(Parser.class);
        File template = new File(tempDirectory, "template.txt");
        when(modeFile.readFileInput(template.getName(), template.getPath())).thenReturn("""
                from : #{MamukaKiknadze@gmail.com}
                to : #{GiorgiDolidze@gmail.com}
                subject : #{introduction meeting}
                text : #{Hello, you are invited to introductory meeting of out training}
                """);
        ByteArrayInputStream in = new ByteArrayInputStream("yes".getBytes());
        System.setIn(in);
        String expected = """
                from : MamukaKiknadze@gmail.com
                to : GiorgiDolidze@gmail.com
                subject : introduction meeting
                text : Hello, you are invited to introductory meeting of out training
                """;
        Assertions.assertEquals(expected, App.getMainInstance().chooseModeAndGiveAnswer(template.getName(), template.getPath(), input, modeFile, parser, fields));
    }
}
