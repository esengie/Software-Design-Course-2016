package ru.spbau.mit.Piping;


import ru.spbau.mit.Command.Argument;
import ru.spbau.mit.Command.Command;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Pipe {

    static class PipedCommand extends Command {
        private Command m_inCommand;
        private Command m_outCommand;

        PipedCommand(List<Argument> a_args) {
            super(a_args);
        }

        void setInOutCommand(Command a_left, Command a_right) throws IOException {
            m_inCommand = a_left;
            setInputStream(a_left.getInputStream());

            m_outCommand = a_right;
            setOutputStream(a_right.getOutputStream());

            PipedOutputStream out = new PipedOutputStream();
            PipedInputStream in = new PipedInputStream(out);

            m_inCommand.setOutputStream(out);
            m_outCommand.setInputStream(in);
        }

        public void run() throws IOException {
            m_inCommand.run();
            m_inCommand.getOutputStream().close();
            m_outCommand.run();
        }
    }

    public static Command connect(Command a_in, Command a_out) throws IOException {
        PipedCommand pipe = new PipedCommand(new ArrayList<>());
        pipe.setInOutCommand(a_in, a_out);

        return pipe;
    }
}
