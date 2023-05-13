package com.messenger.template;

import java.util.List;

public class TemplateGenerator {
    public static String generateTemplate(List<String> values) {
        String value = "#{value}";
        String template = "";
        for (String val : values) {
            template += val + " : " + value + "\n";
        }
        return template;
    }


}
