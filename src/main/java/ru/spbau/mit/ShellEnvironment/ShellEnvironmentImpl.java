package ru.spbau.mit.ShellEnvironment;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple implementation of the ShellEnvironment interface using a HashMap
 */
public class ShellEnvironmentImpl implements ShellEnvironment {
    private Map<String, String> env = new HashMap<>();

    public boolean checkDefined(String varName) {
        return env.containsKey(varName);
    }

    public String getValue(String varName) {
        return env.get(varName);
    }

    public void addToEnvironment(String varName, String varValue) {
        env.put(varName, varValue);
    }

}
