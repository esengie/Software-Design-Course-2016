package ru.spbau.mit.App;

import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayInputStream;

public class ShellTest {

    @Ignore
    @Test
    public void run() throws Exception {
        Shell.getEnv().addToEnvironment("HOME", "elsewhere");
        ByteArrayInputStream in = new ByteArrayInputStream("echo \"$HOME\"".getBytes());
        System.setIn(in);

        Shell.run();
    }

}