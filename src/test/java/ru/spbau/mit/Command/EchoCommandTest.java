package ru.spbau.mit.Command;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


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

        assertThat(outContent.toString(), is("myfile.txt notmyfile.txt \n"));
    }

}