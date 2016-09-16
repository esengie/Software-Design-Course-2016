package ru.spbau.mit.InputProcessing;

import ru.spbau.mit.Util.Pair;

import java.util.*;

// Reads an environment variable from a string if it's there
public class EnvironmentReader {
    public static Optional<Pair<String, String>> getEnvironmentVariable(String a_stringIn){
        List<String> tempList = new ArrayList<>(Arrays.asList(a_stringIn.split("\\s+")));
        tempList.removeAll(Collections.singleton(""));

        if (tempList.size() > 1)
            return Optional.empty();
        String tempString = tempList.get(0);

        tempList = Arrays.asList(tempString.split("="));
        if (tempList.size() != 2)
            return Optional.empty();

        Pair<String, String> retVal = new Pair<>(tempList.get(0), tempList.get(1));
        return Optional.of(retVal);
    }
}
