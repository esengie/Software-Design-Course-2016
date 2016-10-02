package ru.spbau.mit.Command;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Abstract shell command class that contains args and
 * input output streams for uniform manipulation
 */
public abstract class Command {
    protected final List<String> args;
    private InputStream inStream = System.in;
    private OutputStream outStream = System.out;
    // error stream?


    /**
     * Construct a command
     *
     * @param args arguments
     */
    public Command(final List<String> args) {
        this.args = args;
    }

    /**
     * Setter of the input
     *
     * @param in input
     */
    public void setInputStream(InputStream in) {
        inStream = in;
    }

    /**
     * Setter of the output
     *
     * @param out output
     */
    public void setOutputStream(OutputStream out) {
        outStream = out;
    }

    /**
     * Getter of the input
     */
    public InputStream getInputStream() {
        return inStream;
    }

    /**
     * Getter of the output
     */
    public OutputStream getOutputStream() {
        return outStream;
    }


    /**
     * Default run interface - throws IOexception
     *
     * @throws IOException IO operations everywhere
     */
    public void run() throws IOException {
        throw new UnsupportedOperationException("method not implemented");
    }

    /**
     * Flushes the output stream
     *
     * @throws IOException may throw
     */
    public void flush() throws IOException {
        getOutputStream().flush();
    }
}
