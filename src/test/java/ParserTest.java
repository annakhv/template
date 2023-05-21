import com.messenger.exceptions.RequiredFiledsMissingException;
import com.messenger.exceptions.TemplateFieldsEmptyException;
import com.messenger.template.Parser;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.rules.ExpectedException;

import java.util.List;
import java.util.Map;

public class ParserTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void parseTemplateWithInvalidTemplateInputTest() {
        Parser parser = Parser.getParserInstance();
        String inputTemplate = """
                from : #{MamukaKiknadze@gmail.com}
                to : #{value}
                subject : #{introduction meeting}
                text : #{Hello, you are invited to introductory meeting of out training}
                """;
        List<String> list = List.of("from", "to", "subject", "text");
        exceptionRule.expect(TemplateFieldsEmptyException.class);
        exceptionRule.expectMessage("template fields should not be empty");
        parser.parseTemplate(inputTemplate, list);
    }

    @Test
    public void parseTemplateWithValidTemplateInputTest() {
        Parser parser = Parser.getParserInstance();
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
        Assertions.assertEquals(expected, parser.parseTemplate(inputTemplate, list), "template map is not correctly generated");
    }

    @Test
    public void parseTemplateWithExtraInputTest() {
        Parser parser = Parser.getParserInstance();
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
        Assertions.assertEquals(expected, parser.parseTemplate(inputTemplate, list), "template map is not correctly generated");
    }

    @Test
    public void parseTemplatewithInvalidTemplateInputTest() {
        Parser parser = Parser.getParserInstance();
        String inputTemplate = """
                from : #{MamukaKiknadze@gmail.com}
                to : #{GiorgiDolidze@gmail.com}
                cc : #{MakaDolidze@gmail.com,NinoNino1998@gmail.com }
                subject : #{introduction meeting}
                """;
        List<String> list = List.of("from", "to", "subject", "text");
        exceptionRule.expect(RequiredFiledsMissingException.class);
        exceptionRule.expectMessage("fields are missing from " + list);
        parser.parseTemplate(inputTemplate, list);
    }
}
