package ru.spbau.mit.InputProcessing;

import org.junit.Before;
import org.junit.Test;
import ru.spbau.mit.ShellEnvironment.ShellEnvironment;
import ru.spbau.mit.ShellEnvironment.ShellEnvironmentImpl;

import static org.junit.Assert.*;

public class VariableSubstituterTest {
    private static ShellEnvironment m_env = new ShellEnvironmentImpl();

    static {
        m_env.addToEnvironment("lol", "rol");
        m_env.addToEnvironment("dol", "bol");
    }

    @Test
    public void substituteVariables() throws Exception {
        String test = VariableSubstituter.substituteVariables("$lol \"$dol\" '$lol' $dol", m_env);
        assertEquals(test, "rol \"bol\" '$lol' bol");
    }

}