package ru.spbau.mit.Command;

public class ArgumentImpl implements Argument {
    private String m_string;

    public ArgumentImpl(String a_string){
        m_string = a_string;
    }

    @Override
    public String getContents() {
        return m_string;
    }
}
