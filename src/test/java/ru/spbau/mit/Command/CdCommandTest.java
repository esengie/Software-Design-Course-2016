package ru.spbau.mit.Command;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;


public class CdCommandTest {

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
    public void cdHomeTest() throws Exception {
        Command cd = CommandFactory.createCommand("cd");
        cd.run();

        assertEquals(System.getProperty("user.dir"), System.getProperty("user.home"));
    }

    @Test
    public void cdSubdirectoryTest() throws Exception {
        Command cd = CommandFactory.createCommand("cd", "a");
        cd.run();

        assertEquals(System.getProperty("user.dir"), getClass().getResource("/tests/a").getPath());
    }

}
