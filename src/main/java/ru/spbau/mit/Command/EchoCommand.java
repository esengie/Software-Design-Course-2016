package ru.spbau.mit.Command;

import java.io.IOException;
import java.util.List;

public class EchoCommand extends Command {
    EchoCommand(List<Argument> a_args) {
        super(a_args);
    }

    @Override
    public void run() throws IOException {
        for (Argument arg : m_args){
            getOutputStream().write((arg.getContents() + " ").getBytes());
        }
        getOutputStream().write("\n".getBytes());

        flush();
    }
}
