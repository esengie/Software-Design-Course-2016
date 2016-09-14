package ru.spbau.mit.ShellEnvironment;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ShellEnvironmentImpl implements ShellEnvironment {
    private Map<String, String> m_env= new ConcurrentHashMap<>();

    public boolean checkDefined(String a_varName){
        return m_env.containsKey(a_varName);
    }

    public String getValue(String a_varName){
        return m_env.get(a_varName);
    }

    public void addToEnvironment(String a_varName, String a_varValue){
        m_env.put(a_varName, a_varValue);
    }

}
