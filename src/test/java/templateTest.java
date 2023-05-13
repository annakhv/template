import com.messenger.template.Parser;
import com.messenger.template.TemplateGenerator;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.Map;

public class templateTest {

    @Test
    public void checkTemplateFormTest() {
        List<String> list = List.of("from", "to", "subject", "text");
        String actual = TemplateGenerator.generateTemplate(list);
        String expected = """
                from : #{value}
                to : #{value}
                subject : #{value}
                text : #{value}
                """;
        Assertions.assertEquals(expected, actual, "template with fields" + list + "is not correctly generated ");

    }

    @Test
    public void parseTemplateWithInvalidTemplateInputTest() {
        String inputTemplate = """
                from : #{MamukaKiknadze@gmail.com}
                to : #{value}
                subject : #{introduction meeting}
                text : #{Hello, you are invited to introductory meeting of out training}
                """;
        List<String> list = List.of("from", "to", "subject", "text");
        Assertions.assertThrows(RuntimeException.class, () -> Parser.parseTemplate(inputTemplate,list));
    }

    @Test
    public void parseTemplateWithValidTemplateInputTest() {
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
        Assertions.assertEquals(expected, Parser.parseTemplate(inputTemplate,list), "template map is not correctly generated");
    }
    @Test
    public void parseTemplateWithExtraInputTest() {
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
        Assertions.assertEquals(expected, Parser.parseTemplate(inputTemplate,list), "template map is not correctly generated");
    }
}
