import com.messenger.mode.FileMode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;

public class FileModeTest {

    @TempDir
    File tempDirectory;

    @Test
    public void fileModeWriteAndReadTest() {
        File template = new File(tempDirectory, "template.txt");
        String inputAndOutput = """
                from : #{value}
                to : #{value}
                subject : #{value}
                text : #{value}
                """;
        FileMode fileMode = FileMode.getFileModeInstace();
        fileMode.writeFileOutPut(template.getName(), template.getPath(), inputAndOutput);
        String actual = fileMode.readFileInput(template.getName(), template.getPath());
        Assertions.assertEquals(inputAndOutput,actual);

    }
}
