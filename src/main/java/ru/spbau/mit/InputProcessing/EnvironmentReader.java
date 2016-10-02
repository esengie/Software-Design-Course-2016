package ru.spbau.mit.InputProcessing;

import ru.spbau.mit.Util.ImmutablePair;

import java.util.*;

/**
 * Reads an environment variable from a string if it's there
 */
class EnvironmentReader {

    /**
     * If the line input is in the form var=val returns the pair
     *
     * @param stringIn input
     * @return optional pair of variable and its value
     */
    static Optional<ImmutablePair<String, String>> getEnvironmentVariable(String stringIn) {
        List<String> tempList = new ArrayList<>(Arrays.asList(stringIn.split("\\s+")));
        tempList.removeAll(Collections.singleton(""));

        if (tempList.size() > 1) {
            return Optional.empty();
        }
        String tempString = tempList.get(0);

        tempList = Arrays.asList(tempString.split("="));
        if (tempList.size() != 2) {
            return Optional.empty();
        }

        ImmutablePair<String, String> retVal = new ImmutablePair<>(tempList.get(0), tempList.get(1));
        return Optional.of(retVal);
    }
}
