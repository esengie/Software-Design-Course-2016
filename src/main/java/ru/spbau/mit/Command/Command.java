package ru.spbau.mit.Command;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public abstract class Command {
    protected final List<Argument> m_args;
    protected InputStream m_inStream;
    protected OutputStream m_outStream;
    // error stream?

    protected Command(final List<Argument> a_args){
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

    public void run() {
        throw new UnsupportedOperationException("method not implemented");
    }
}
