package ru.spbau.mit.Command;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class WcCommand extends Command {
    WcCommand(List<Argument> a_args) {
        super(a_args);
    }

    @Override
    public void run() throws IOException {
        long linesTotal = 0;
        long wordsTotal = 0;
        long bytesTotal = 0;

        for (Argument arg : m_args) {

            File file = new File(arg.getContents());
            long lines = 0;
            long words = 0;
            long bytesL = file.length();

            String temp;
            BufferedReader reader = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            while ((temp = reader.readLine()) != null) {
                lines++;
                words += temp.split("\\s").length;
            }
            writeToOutput(lines, words, bytesL, arg.getContents());

            reader.close();

            linesTotal += lines;
            wordsTotal += words;
            bytesTotal += bytesL;
        }
        if (m_args.size() > 1)
            writeToOutput(linesTotal, wordsTotal, bytesTotal, "total");

        flush();
    }

    private void writeToOutput(long lines, long words, long bytesL, String name) throws IOException {
        String output = String.format("%1$d %2$d %3$d " + name + "\n",
                lines, words, bytesL);

        getOutputStream().write(output.getBytes());
    }
}
