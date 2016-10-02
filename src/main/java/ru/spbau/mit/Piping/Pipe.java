package ru.spbau.mit.Piping;


import ru.spbau.mit.Command.Argument;
import ru.spbau.mit.Command.Command;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * A pipe class - connects two commands together
 */
public class Pipe {


    private static class PipedCommand extends Command {
        private Command inCommand;
        private Command outCommand;

        PipedCommand(List<Argument> args) {
            super(args);
        }

        void setInOutCommand(Command left, Command right) throws IOException {
            inCommand = left;
            setInputStream(left.getInputStream());

            outCommand = right;
            setOutputStream(right.getOutputStream());

            PipedOutputStream out = new PipedOutputStream();
            PipedInputStream in = new PipedInputStream(out);

            inCommand.setOutputStream(out);
            outCommand.setInputStream(in);
        }

        public void run() throws IOException {
            inCommand.run();
            inCommand.getOutputStream().close();
            outCommand.run();
        }
    }

    /**
     * Connects two commands via a pipe
     *
     * @param in left command
     * @param out right command
     * @return piped command
     * @throws IOException working with stream it ma
     */
    public static Command connect(Command in, Command out) throws IOException {
        PipedCommand pipe = new PipedCommand(new ArrayList<>());
        pipe.setInOutCommand(in, out);

        return pipe;
    }
}
