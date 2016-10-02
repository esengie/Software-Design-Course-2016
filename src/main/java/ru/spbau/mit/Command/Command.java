package ru.spbau.mit.Command;

import java.io.*;
import java.util.List;

/**
 * Abstract shell command class that contains args and
 * input output streams for uniform manipulation
 */
public abstract class Command {
    final List<Argument> args;
    private InputStream inStream = System.in;
    private OutputStream outStream = System.out;
    // error stream?

    public Command(final List<Argument> args){
        this.args = args;
    }

    public void setInputStream(InputStream in){
        inStream = in;
    }

    public void setOutputStream(OutputStream out){
        outStream = out;
    }

    public InputStream getInputStream(){
        return inStream;
    }

    public OutputStream getOutputStream(){
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

    protected void flush() throws IOException {
        getOutputStream().flush();
    }
}
