package ru.spbau.mit.InputProcessing;

import ru.spbau.mit.Exceptions.VariableUndefinedException;
import ru.spbau.mit.ShellEnvironment.ShellEnvironment;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Substitutes all the vars in the environment into the line
 */
class VariableSubstituter {

    /**
     * Substitutes rather orcishly but does it's job
     *
     * @param stringIn input string
     * @param env shell env
     * @return processed string
     * @throws VariableUndefinedException if the var isn't in the env
     */
    static String substituteVariables(String stringIn, ShellEnvironment env) throws VariableUndefinedException {

        List<Integer> dollarPositions = new ArrayList<>();
        boolean inSingleQuotes = false;
        for (int i = 0; i < stringIn.length() - 1; ++i) {
            char c = stringIn.charAt(i);
            if (!inSingleQuotes && c == '$') {
                dollarPositions.add(i);
            }
            if (c == '\'')
                inSingleQuotes = !inSingleQuotes;
        }

        StringBuilder retVal = new StringBuilder();
        int left = 0;
        Set<Character> specialCharacters = new TreeSet<>(Arrays.asList(' ', '"', '/', '='));

        for (Integer right : dollarPositions) {
            retVal.append(stringIn.substring(left, right));
            left = right + 1;
            while (left < stringIn.length()
                    && !specialCharacters.contains(stringIn.charAt(left)))
                ++left;

            String varName = stringIn.substring(right+1, left);

            if (!env.checkDefined(varName))
                throw new VariableUndefinedException(varName);
            retVal.append(env.getValue(varName));
        }
        retVal.append(stringIn.substring(left));

        return retVal.toString();
    }
}
