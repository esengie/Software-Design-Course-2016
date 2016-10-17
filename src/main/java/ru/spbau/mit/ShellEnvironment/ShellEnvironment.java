package ru.spbau.mit.ShellEnvironment;

/**
 * Shell Env interface
 */
public interface ShellEnvironment {

    /**
     * Checks if the variable is defined
     *
     * @param varName Variable name
     * @return check
     */
    boolean checkDefined(String varName);


    /**
     * Gets the value of the var
     *
     * @param varName Variable name
     * @return value
     */
    String getValue(String varName);

    /**
     * Adds a  var to environment
     *
     * @param varName  Variable name
     * @param varValue Variable value
     */
    void addToEnvironment(String varName, String varValue);
}
