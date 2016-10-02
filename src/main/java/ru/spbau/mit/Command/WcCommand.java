package ru.spbau.mit.Command;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Wc - reads each file passed and counts lines, words and bytes
 * in each files and the total
 */
class WcCommand extends Command {
    WcCommand(List<String> args) {
        super(args);
    }

    /**
     * Iterates through the args and counts the stats
     * <p>
     * Prints the total if there are more than two files specified
     *
     * @throws IOException could
     */
    @Override
    public void run() throws IOException {
        long linesTotal = 0;
        long wordsTotal = 0;
        long bytesTotal = 0;

        for (String arg : args) {

            File file = new File(arg);
            long lines = 0;
            long words = 0;
            long bytesL = file.length();

            String temp;
            BufferedReader reader = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            while ((temp = reader.readLine()) != null) {
                lines++;
                words += temp.split("\\s").length;
            }
            writeToOutput(lines, words, bytesL, arg);

            reader.close();

            linesTotal += lines;
            wordsTotal += words;
            bytesTotal += bytesL;
        }
        if (args.size() > 1) {
            writeToOutput(linesTotal, wordsTotal, bytesTotal, "total");
        }
        flush();
    }

    private void writeToOutput(long lines, long words, long bytesL, String name) throws IOException {
        String output = String.format("%1$d %2$d %3$d " + name + "\n",
                lines, words, bytesL);

        getOutputStream().write(output.getBytes());
    }
}
