package ru.spbau.mit.App;

import org.junit.Test;

import java.io.ByteArrayInputStream;

public class ShellTest {
    @Test
    public void run() throws Exception {
        ByteArrayInputStream in = new ByteArrayInputStream("echo \"$HOME\"".getBytes());
        System.setIn(in);

        Shell.run();
    }

}