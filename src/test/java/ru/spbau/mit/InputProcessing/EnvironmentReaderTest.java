package ru.spbau.mit.InputProcessing;

import org.junit.Test;
import ru.spbau.mit.Util.ImmutablePair;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;


public class EnvironmentReaderTest {
    @Test
    public void getEnvironmentVariable() throws Exception {
        Optional<ImmutablePair<String, String>> test =
                EnvironmentReader.getEnvironmentVariable(" moomm=23  ");
        assertTrue(test.isPresent());
        assertThat(test.get().first, is("moomm"));
        assertThat(test.get().second, is("23"));

        test = EnvironmentReader.getEnvironmentVariable(" echo \"moomm=23\"");
        assertFalse(test.isPresent());

        test = EnvironmentReader.getEnvironmentVariable(" moomm = 23  ");
        assertFalse(test.isPresent());

        test = EnvironmentReader.getEnvironmentVariable("pwd");
        assertFalse(test.isPresent());
    }

}