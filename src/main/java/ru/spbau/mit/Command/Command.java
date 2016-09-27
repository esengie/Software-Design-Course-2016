package ru.spbau.mit.Command;

import java.io.*;
import java.util.List;

/**
 * Abstract shell command class that contains args and
 * input output streams for uniform manipulation
 */
public abstract class Command {
    final List<Argument> m_args;
    private InputStream m_inStream = System.in;
    private OutputStream m_outStream = System.out;
    // error stream?

    public Command(final List<Argument> a_args){
        m_args = a_args;
    }

    public void setInputStream(InputStream a_in){
        m_inStream = a_in;
    }

    public void setOutputStream(OutputStream a_out){
        m_outStream = a_out;
    }

    public InputStream getInputStream(){
        return m_inStream;
    }

    public OutputStream getOutputStream(){
        return m_outStream;
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
