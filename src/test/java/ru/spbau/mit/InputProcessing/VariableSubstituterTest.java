package ru.spbau.mit.InputProcessing;

import org.junit.Test;
import ru.spbau.mit.ShellEnvironment.ShellEnvironment;
import ru.spbau.mit.ShellEnvironment.ShellEnvironmentImpl;

import static org.junit.Assert.*;

public class VariableSubstituterTest {
    private static ShellEnvironment env = new ShellEnvironmentImpl();

    static {
        env.addToEnvironment("lol", "rol");
        env.addToEnvironment("dol", "bol");
    }

    @Test
    public void substituteVariables() throws Exception {
        String test = VariableSubstituter.substituteVariables("$lol \"$dol\" '$lol' $dol", env);
        assertEquals(test, "rol \"bol\" '$lol' bol");
    }

}