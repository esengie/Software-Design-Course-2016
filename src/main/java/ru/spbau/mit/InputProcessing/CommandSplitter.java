package ru.spbau.mit.InputProcessing;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Splits a line on |
 */
class CommandSplitter {
    /**
     * Splits a line on pipe symbols
     * Can't just split on '|' because it can be inside the brackets
     * @param stringIn input
     * @return list of split strings
     */
    static List<String> splitByPipe(String stringIn){
        List<String> retVal = new ArrayList<>();

        boolean inSingleQuotes = false;
        boolean inDoubleQuotes = false;
        int left = 0;
        for (int right = 0; right < stringIn.length(); ++right){
            char c = stringIn.charAt(right);
            if (!inSingleQuotes && !inDoubleQuotes && c == '|'){
                retVal.add(stringIn.substring(left, right));
                left = right + 1;
            }
            if (c == '\'')
                inSingleQuotes = !inSingleQuotes;
            if (c == '\"')
                inDoubleQuotes = !inDoubleQuotes;
        }
        retVal.add(stringIn.substring(left));

        for (int i = 0; i < retVal.size(); ++i){
            retVal.set(i, retVal.get(i).trim());
        }

        return retVal;
    }
}
