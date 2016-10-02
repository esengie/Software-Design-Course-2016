package ru.spbau.mit.Command;

import com.google.common.io.ByteStreams;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * Cat command -- gets the input stream to output stream
 * if no args are passed, otherwise copies files to outputstream
 */
class CatCommand extends Command {
    CatCommand(List<String> args) {
        super(args);
    }

    /**
     * If no args are passed gets the input stream to output stream
     * otherwise copies files to outputstream
     *
     * @throws IOException could throw
     */
    @Override
    public void run() throws IOException {
        if (args.size() == 0) {
            ByteStreams.copy(getInputStream(), getOutputStream());
        }
        for (String arg : args) {
            // Stupid java can't get path get
            File f = new File(arg).getAbsoluteFile();
            Files.copy(f.toPath(), getOutputStream());
            getOutputStream().write("\n".getBytes());
        }

        flush();
    }
}
