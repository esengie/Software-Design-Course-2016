package ru.spbau.mit.Command;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class EchoCommandTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

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
        String[] files = {"myfile.txt", "notmyfile.txt"};

        Command echo = CommandFactory.createCommand("echo", files);
        echo.run();

        assertEquals("myfile.txt notmyfile.txt \n", outContent.toString());
    }

}