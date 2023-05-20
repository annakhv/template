import com.messenger.template.TemplateGenerator;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;


public class TemplateTest {



    @Test
    public void checkTemplateFormTest() {
        TemplateGenerator templateGenerator=TemplateGenerator.getTemplateGenerator();
        List<String> list = List.of("from", "to", "subject", "text");
        String actual = templateGenerator.generateTemplate(list);
        String expected = """
                from : #{value}
                to : #{value}
                subject : #{value}
                text : #{value}
                """;
        Assertions.assertEquals(expected, actual, "template with fields" + list + "is not correctly generated ");

    }

}
