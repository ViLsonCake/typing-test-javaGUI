package com.project.json;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WorkWithJson {
    public static void writeToJson(List<String> words) throws IOException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put(JsonConsts.listAttributeName, words);

        FileWriter file = new FileWriter("src/main/resources/json/words.json", false);
        file.write(jsonObject.toJSONString());
        file.close();

        System.out.println("Success");
    }

    public static List<String> readFromJson() throws IOException, ParseException {
        // Create json parser
        JSONParser parser = new JSONParser();

        // parsing values from json file
        JSONObject readJson = (JSONObject) parser.parse(new FileReader("src/main/resources/json/words.json"));

        return (List<String>) readJson.get(JsonConsts.listAttributeName);
    }
}
