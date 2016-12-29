package ru.spbau.mit.Command;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class LsCommandTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setProperty("user.dir", getClass().getResource("/tests").getPath());
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }

    @Test
    public void lsCurrentDirectory() throws Exception {
        Command ls = CommandFactory.createCommand("ls");
        ls.run();

        assertEquals(outContent.toString(), "a b \n");
    }

    @Test
    public void lsOneDirectory() throws Exception {
        Command ls = CommandFactory.createCommand("ls", "a");
        ls.run();

        assertEquals(outContent.toString(), "1 2 \n");
    }

    @Test
    public void lsTwoDirectories() throws Exception {
        Command ls = CommandFactory.createCommand("ls", "a", "b");
        ls.run();

        assertEquals(outContent.toString(), "a:\n1 2 \nb:\n3 4 \n");
    }
}
