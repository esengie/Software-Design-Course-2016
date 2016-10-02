package ru.spbau.mit.InputProcessing;

import org.junit.Before;
import org.junit.Test;
import ru.spbau.mit.ShellEnvironment.ShellEnvironment;
import ru.spbau.mit.ShellEnvironment.ShellEnvironmentImpl;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CommandParserTest {

    ShellEnvironment env;

    @Before
    public void setup() {
        env = new ShellEnvironmentImpl();
        env.addToEnvironment("rol", "lol");
        env.addToEnvironment("dol", "bol");
    }

    @Test
    public void processInput() throws Exception {
        String input = " pwd | l=asdf | echo \"$dol=23\" | $l";
        List<String> expected = Arrays.asList("pwd", "echo \"bol=23\"", "asdf");

        List<String> test = CommandParser.processInput(input, env);

        assertThat(test, is(expected));
    }

    @Test
    public void parse() throws Exception {

    }

}