package com.messenger.template;

import java.util.Map;

public class Parser {
    public static Map<String,String> parseTemplate(String template){
        String valueTemplate="#{value}";
      String[] values=template.split("\\n");
      for (String val:values){
          if (val.contains(valueTemplate)){
              throw new RuntimeException("template fields should not be empty");
          }
      }
      return null;
    }
}
