package ru.spbau.mit.Command;

import com.sun.org.apache.xpath.internal.Arg;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class CatCommandTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void run() throws Exception {
        String[] files = {"myfile.txt", "notmyfile.txt"};
        final File file1 = folder.newFile(files[0]);
        final File file2 = folder.newFile(files[1]);

        System.setProperty("user.dir", folder.getRoot().getAbsolutePath());

        OutputStream wr = new FileOutputStream(file1);
        wr.write("asasdsada   sdasdasd".getBytes());
        wr.close();

        wr = new FileOutputStream(file2);
        wr.write("My name is Tom".getBytes());
        wr.close();

        Command cat = CommandFactory.createCommand("cat", files);
        cat.run();

        assertEquals("asasdsada   sdasdasd\nMy name is Tom\n", outContent.toString());
    }

}