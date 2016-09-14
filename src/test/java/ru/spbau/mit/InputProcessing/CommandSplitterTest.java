package ru.spbau.mit.InputProcessing;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class CommandSplitterTest {
    @Test
    public void splitByPipe() throws Exception {
        String test = " cat \"asdas|dasd\" | pwd | cat";
        List<String> expected = Arrays.asList(" cat \"asdas|dasd\" ", " pwd ", " cat");

        assertThat(CommandSplitter.splitByPipe(test), is(expected));
    }

}