package ru.spbau.mit.Command;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import ru.spbau.mit.Piping.Pipe;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class GrepCommandTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private List<String> files;
    private final String input = "    URL resource = this.getClass().getResource(\"/path/to/resource.res\");\n" +
            "    File is = null;\n" +
            "    try {\n" +
            "        is = new File(resource.toURI());\n" +
            "    } catch (URISyntaxException e1) {\n" +
            "        // TODO Auto-generated catch block\n" +
            "        e1.printStackTrace();\n" +
            "    }\n" +
            "    try {\n" +
            "        FileInputStream input = new FileInputStream(is);\n" +
            "    } catch (FileNotFoundException e1) {\n" +
            "        // TODO Auto-generated catch block\n" +
            "        e1.printStackTrace();\n" +
            "    }";


    @Before
    public void setUpStreams() throws IOException {
        files = new ArrayList<>(Arrays.asList("myfile.txt", "notmyfile.txt"));
        final File file1 = folder.newFile(files.get(0));
        final File file2 = folder.newFile(files.get(1));

        System.setProperty("user.dir", folder.getRoot().getAbsolutePath());

        OutputStream wr = new FileOutputStream(file1);
        wr.write(input.getBytes());
        wr.close();

        wr = new FileOutputStream(file2);
        wr.write("My name is Tom".getBytes());
        wr.close();

        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private String expected = "myfile.txt:     } catch (URISyntaxException e1) {\n" +
            "myfile.txt:         // TODO Auto-generated catch block\n" +
            "myfile.txt:     } catch (FileNotFoundException e1) {\n" +
            "myfile.txt:         // TODO Auto-generated catch block\n";

    @Test
    public void runSimple() throws Exception {
        files.add(0, "cat*");
        Command cat = CommandFactory.createCommand("grep", files.toArray(new String[0]));
        cat.run();
        assertThat(outContent.toString(), is(expected));
    }

    @Test
    public void runCaseInsensitivePass() throws Exception {
        files.add(0, "CAT*");
        files.add(1, "-i");
        Command cat = CommandFactory.createCommand("grep", files.toArray(new String[0]));
        cat.run();
        assertThat(outContent.toString(), is(expected));
    }

    @Test
    public void runCaseInsensitiveFail() throws Exception {
        files.add(0, "CAT*");
        Command cat = CommandFactory.createCommand("grep", files.toArray(new String[0]));
        cat.run();
        assertThat(outContent.toString(), is(""));
    }

    @Test
    public void runWordsPass() throws Exception {
        files.add(0, "catch");
        files.add(1, "-w");
        Command cat = CommandFactory.createCommand("grep", files.toArray(new String[0]));
        cat.run();
        assertThat(outContent.toString(), is(expected));
    }

    @Test
    public void runWordsFail() throws Exception {
        files.add(0, "catc*");
        files.add(1, "-w");
        Command cat = CommandFactory.createCommand("grep", files.toArray(new String[0]));
        cat.run();
        assertThat(outContent.toString(), is(""));
    }

    @Test
    public void runAn() throws Exception {
        files.add(0, "catch");
        files.add(1, "-A");
        files.add(2, "12");
        Command cat = CommandFactory.createCommand("grep", files.toArray(new String[0]));
        cat.run();
        assertThat(outContent.toString(), is(
                "myfile.txt:     } catch (URISyntaxException e1) {\n" +
                        "myfile.txt:         // TODO Auto-generated catch block\n" +
                        "myfile.txt:         e1.printStackTrace();\n" +
                        "myfile.txt:     }\n" +
                        "myfile.txt:     try {\n" +
                        "myfile.txt:         FileInputStream input = new FileInputStream(is);\n" +
                        "myfile.txt:     } catch (FileNotFoundException e1) {\n" +
                        "myfile.txt:         // TODO Auto-generated catch block\n" +
                        "myfile.txt:         e1.printStackTrace();\n" +
                        "myfile.txt:     }\n"));
    }

    @Test
    public void piped() throws Exception {
        Command cat = CommandFactory.createCommand("cat", files.get(0));
        Command grep = CommandFactory.createCommand("grep", "catch", "-w");
        Command pip = Pipe.connect(cat, grep);
        pip.run();
        assertThat(outContent.toString(), is(
                "    } catch (URISyntaxException e1) {\n" +
                "        // TODO Auto-generated catch block\n" +
                "    } catch (FileNotFoundException e1) {\n" +
                "        // TODO Auto-generated catch block\n"));
    }

}