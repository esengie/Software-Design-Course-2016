package ru.spbau.mit.Command;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Pwd command
 */
class PwdCommand extends Command {
    PwdCommand(List<Argument> args) {
        super(args);
    }

    /**
     * Return where we are in the filesystem
     *
     * @throws IOException io is hard
     */
    @Override
    public void run() throws IOException {
        // Paths.get("") y u no work?
        Path currentRelativePath = new File("").getAbsoluteFile().toPath();
        String s = currentRelativePath.toAbsolutePath().toString() + "\n";
        getOutputStream().write(s.getBytes());

        flush();
    }

}
