package ru.spbau.mit.Command;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class PwdCommandTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }

    @Test
    public void run() throws Exception {
        String path = folder.getRoot().getAbsolutePath();
        System.setProperty("user.dir", path);

        Command pwd = CommandFactory.createCommand("pwd");
        pwd.run();

        assertEquals(path + "\n", outContent.toString());
    }
}