package com.project.random;

import com.project.json.WorkWithJson;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class RandomWords {

    public String randomSample(int length) throws IOException, ParseException {

        // Output string
        String output = "";

        // Parse words list from json file
        List<String> wordsList = WorkWithJson.readFromJson();

        // Concat result string with random words
        for (int i = 0; i <= length; i++) {
            // Get random index
            int randomIndex = new Random().nextInt(wordsList.size());
            // Discarding empty values
            if (!(wordsList.get(randomIndex).isEmpty()))
                output = output.concat(wordsList.get(randomIndex) + " ");
        }

        return output.trim();
    }
}
