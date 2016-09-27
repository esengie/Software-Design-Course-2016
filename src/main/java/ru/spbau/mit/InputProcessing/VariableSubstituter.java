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
     * @param a_stringIn input string
     * @param a_env shell env
     * @return processed string
     * @throws VariableUndefinedException if the var isn't in the env
     */
    static String substituteVariables(String a_stringIn, ShellEnvironment a_env) throws VariableUndefinedException {

        List<Integer> dollarPositions = new ArrayList<>();
        boolean inSingleQuotes = false;
        for (int i = 0; i < a_stringIn.length() - 1; ++i) {
            char c = a_stringIn.charAt(i);
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
            retVal.append(a_stringIn.substring(left, right));
            left = right + 1;
            while (left < a_stringIn.length()
                    && !specialCharacters.contains(a_stringIn.charAt(left)))
                ++left;

            String varName = a_stringIn.substring(right+1, left);

            if (!a_env.checkDefined(varName))
                throw new VariableUndefinedException(varName);
            retVal.append(a_env.getValue(varName));
        }
        retVal.append(a_stringIn.substring(left));

        return retVal.toString();
    }
}
