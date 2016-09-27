package ru.spbau.mit.Command;

/**
 * Basic implementation of command argument interface
 */
class ArgumentImpl implements Argument {
    private String m_string;

    ArgumentImpl(String a_string){
        m_string = a_string;
    }

    @Override
    public String getContents() {
        return m_string;
    }
}
