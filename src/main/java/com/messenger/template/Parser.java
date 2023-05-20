package com.messenger.template;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Parser {
    public  Map<String,String> parseTemplate(String template, List<String> validFields){
        String valueTemplate="#{value}";
      String[] values=template.split("\\n");
      Map<String,String> map=new LinkedHashMap<>();
      for (String val:values){
          if (val.contains(valueTemplate)){
              throw new RuntimeException("template fields should not be empty");
          }else{
              String[] pair=val.split(":");
              String key=pair[0].trim();
              if(validFields.contains(key)){
                  String value=pair[1].trim();
                  String endValue=value.substring(2,value.length()-1);
                  map.put(key,endValue);
              }

          }
      }
      return map;
    }
}
