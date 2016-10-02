package ru.spbau.mit.InputProcessing;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CommandSplitterTest {
    @Test
    public void splitByPipe() throws Exception {
        String test = " cat \"asdas|dasd\" | pwd | cat";
        List<String> expected = Arrays.asList("cat \"asdas|dasd\"", "pwd", "cat");

        assertThat(CommandSplitter.splitByPipe(test), is(expected));
    }

}