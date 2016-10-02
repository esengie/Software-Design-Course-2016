package ru.spbau.mit.InputProcessing;

import org.junit.Test;
import ru.spbau.mit.Util.Pair;

import java.util.Optional;

import static org.junit.Assert.*;

public class EnvironmentReaderTest {
    @Test
    public void getEnvironmentVariable() throws Exception {
        Optional<Pair<String, String>> test =
                EnvironmentReader.getEnvironmentVariable(" moomm=23  ");
        assertTrue(test.isPresent());
        assertEquals(test.get().first, "moomm");
        assertEquals(test.get().second, "23");

        test = EnvironmentReader.getEnvironmentVariable(" echo \"moomm=23\"");
        assertFalse(test.isPresent());

        test = EnvironmentReader.getEnvironmentVariable(" moomm = 23  ");
        assertFalse(test.isPresent());

        test = EnvironmentReader.getEnvironmentVariable("pwd");
        assertFalse(test.isPresent());
    }

}