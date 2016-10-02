package ru.spbau.mit.Command;

/**
 * Basic implementation of command argument interface
 */
class ArgumentImpl implements Argument {
    private String string;

    ArgumentImpl(String string) {
        this.string = string;
    }

    @Override
    public String getContents() {
        return string;
    }
}
