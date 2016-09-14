package ru.spbau.mit.InputProcessing;


import java.util.ArrayList;
import java.util.List;

public class CommandSplitter {
    // Can't just split on '|' because it can be inside the brackets
    public static List<String> splitByPipe(String a_stringIn){
        List<String> retVal = new ArrayList<>();

        boolean inSingleQuotes = false;
        boolean inDoubleQuotes = false;
        int left = 0;
        for (int right = 0; right < a_stringIn.length(); ++right){
            char c = a_stringIn.charAt(right);
            if (!inSingleQuotes && !inDoubleQuotes && c == '|'){
                retVal.add(a_stringIn.substring(left, right));
                left = right + 1;
            }
            if (c == '\'')
                inSingleQuotes = !inSingleQuotes;
            if (c == '\"')
                inDoubleQuotes = !inDoubleQuotes;
        }
        retVal.add(a_stringIn.substring(left));

        return retVal;
    }
}
