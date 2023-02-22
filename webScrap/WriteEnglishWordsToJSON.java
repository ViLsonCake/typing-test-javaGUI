package com.project.webScrap;

import com.project.json.WorkWithJson;
import com.project.random.RandomWords;
import com.project.validate.Validator;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WriteEnglishWordsToJSON {
    public static void main(String[] args) throws IOException, ParseException {
        Document document = Jsoup.connect("https://gonaturalenglish.com/1000-most-common-words-in-the-english-language/").get();

        List<String> wordsList = new ArrayList<>();

        Elements words = document.getElementsByTag("strong");

        for (Element word : words)
            wordsList.add(Validator.deleteExtraCharacters(word.text().trim()));

        // Write to list to json file
        WorkWithJson.writeToJson(wordsList);

    }
}
