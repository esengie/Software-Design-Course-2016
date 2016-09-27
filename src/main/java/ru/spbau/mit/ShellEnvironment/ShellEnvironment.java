package ru.spbau.mit.ShellEnvironment;

/**
 * Shell Env interface
 */
public interface ShellEnvironment {

    /**
     * Checks if the variable is defined
     * @param a_varName Variable name
     * @return check
     */
    boolean checkDefined(String a_varName);


    /**
     * Gets the value of the var
     * @param a_varName Variable name
     * @return value
     */
    String getValue(String a_varName);

    /**
     * Adds a  var to environment
     * @param a_varName Variable name
     * @param a_varValue Variable value
     */
    void addToEnvironment(String a_varName, String a_varValue);
}
