package ru.spbau.mit.Piping;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import ru.spbau.mit.Command.Command;
import ru.spbau.mit.Command.CommandFactory;

import java.io.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class PipedCommandTest {
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final String[] files = {"myfile.txt", "notmyfile.txt"};

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUpStreams() throws IOException {
        System.setOut(new PrintStream(outContent));

        File file1 = folder.newFile(files[0]);
        File file2 = folder.newFile(files[1]);

        System.setProperty("user.dir", folder.getRoot().getAbsolutePath());

        OutputStream wr = new FileOutputStream(file1);
        wr.write("asasdsada   sdasdasd".getBytes());
        wr.close();

        wr = new FileOutputStream(file2);
        wr.write("My name is Tom".getBytes());
        wr.close();
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }

    @Test
    public void runInternal() throws Exception {
        Command cat1 = CommandFactory.createCommand("cat", files);
        Command cat2 = CommandFactory.createCommand("cat");
        Command cat = Pipe.connect(cat1, cat2);
        cat.run();

        assertThat(outContent.toString(), is("asasdsada   sdasdasd\nMy name is Tom\n"));
    }

    @Test
    public void runExternal() throws IOException {
        Command cat = CommandFactory.createCommand("cat", files);
        Command grep = CommandFactory.createCommand("grep", "Tom");
        Command find = Pipe.connect(cat, grep);
        find.run();

        assertThat(outContent.toString(), is("My name is Tom\n"));
    }
}