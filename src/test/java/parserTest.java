import com.messenger.template.Parser;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.Map;

public class parserTest {

    @Test
    public void parseTemplateWithInvalidTemplateInputTest() {
        Parser parser=new Parser();
        String inputTemplate = """
                from : #{MamukaKiknadze@gmail.com}
                to : #{value}
                subject : #{introduction meeting}
                text : #{Hello, you are invited to introductory meeting of out training}
                """;
        List<String> list = List.of("from", "to", "subject", "text");
        Assertions.assertThrows(RuntimeException.class, () -> parser.parseTemplate(inputTemplate,list));
    }

    @Test
    public void parseTemplateWithValidTemplateInputTest() {
        Parser parser=new Parser();
        String inputTemplate = """
                from : #{MamukaKiknadze@gmail.com}
                to : #{GiorgiDolidze@gmail.com}
                subject : #{introduction meeting}
                text : #{Hello, you are invited to introductory meeting of out training}
                """;
        List<String> list = List.of("from", "to", "subject", "text");
        Map<String, String> expected = Map.of("from", "MamukaKiknadze@gmail.com",
                "to", "GiorgiDolidze@gmail.com",
                "subject", "introduction meeting",
                "text", "Hello, you are invited to introductory meeting of out training");
        Assertions.assertEquals(expected, parser.parseTemplate(inputTemplate,list), "template map is not correctly generated");
    }
    @Test
    public void parseTemplateWithExtraInputTest() {
        Parser parser=new Parser();
        String inputTemplate = """
                from : #{MamukaKiknadze@gmail.com}
                to : #{GiorgiDolidze@gmail.com}
                cc : #{MakaDolidze@gmail.com,NinoNino1998@gmail.com }
                subject : #{introduction meeting}
                text : #{Hello, you are invited to introductory meeting of out training}
                """;
        List<String> list = List.of("from", "to", "subject", "text");
        Map<String, String> expected = Map.of("from", "MamukaKiknadze@gmail.com",
                "to", "GiorgiDolidze@gmail.com",
                "subject", "introduction meeting",
                "text", "Hello, you are invited to introductory meeting of out training");
        Assertions.assertEquals(expected, parser.parseTemplate(inputTemplate,list), "template map is not correctly generated");
    }
}
