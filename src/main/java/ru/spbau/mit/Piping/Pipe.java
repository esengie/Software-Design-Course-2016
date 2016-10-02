package ru.spbau.mit.Piping;


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
    /**
     * A decorator class, decorates two commands into one,
     * by redirecting the output of the first into the input of the second
     *
     */
    private static class PipedCommand extends Command {
        private Command inCommand;
        private Command outCommand;

        PipedCommand(List<String> args) {
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

        /**
         * First command runs then the second
         *
         * @throws IOException could throw
         */
        public void run() throws IOException {
            inCommand.run();
            inCommand.getOutputStream().close();
            outCommand.run();
        }
    }

    /**
     * Connects two commands via a pipe (using Piped streams)
     *
     * Command one runs, writes to input of the second,
     * then command two runs
     *
     * @param in command on the left of the pipe
     * @param out command on the right of the pipe
     * @return piped decorated command
     * @throws IOException may throw
     */
    public static Command connect(Command in, Command out) throws IOException {
        PipedCommand pipe = new PipedCommand(new ArrayList<>());
        pipe.setInOutCommand(in, out);

        return pipe;
    }
}
