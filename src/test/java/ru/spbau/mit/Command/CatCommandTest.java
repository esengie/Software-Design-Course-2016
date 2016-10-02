package ru.spbau.mit.Command;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


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

        assertThat(outContent.toString(), is("asasdsada   sdasdasd\nMy name is Tom\n"));
    }

}