package ru.spbau.mit.ShellEnvironment;

public interface ShellEnvironment {
    boolean checkDefined(String a_varName);
    String getValue(String a_varName);
    void addToEnvironment(String a_varName, String a_varValue);
}
