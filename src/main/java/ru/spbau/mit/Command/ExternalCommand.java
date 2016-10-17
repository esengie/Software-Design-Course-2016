package ru.spbau.mit.Command;

import java.io.IOException;
import java.util.List;

/**
 * External command - uses ProcessBuilder to create a process
 */
class ExternalCommand extends Command {
    ExternalCommand(List<String> args) {
        super(args);
    }

    /**
     * Reads all piped input to the process and closes the
     * inputstream of the process and continues till the work is done
     *
     * @throws IOException could
     */
    @Override
    public void run() throws IOException {
        ProcessBuilder pb = new ProcessBuilder(args);
        Process p = pb.start();

        int c;
        if (!getInputStream().equals(System.in)) {
            while ((c = getInputStream().read()) != -1) {
                p.getOutputStream().write(c);
            }
        }
        p.getOutputStream().flush();
        p.getOutputStream().close();

        while ((c = p.getInputStream().read()) != -1) {
            getOutputStream().write(c);
        }

        flush();
    }
}
