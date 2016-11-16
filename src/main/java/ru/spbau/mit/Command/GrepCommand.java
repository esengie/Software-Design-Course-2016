package ru.spbau.mit.Command;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import ru.spbau.mit.Exceptions.TooFewArgumentsException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Grep - gets a pattern and outputs finds from input
 *
 * -i - Case insensitive
 * -w - Whole words search
 * -A n - add lines after the one found
 *
 */
class GrepCommand extends Command {
    GrepCommand(List<String> args) {
        super(args);
    }

    @Parameter(names = "-i", description = "Is case-insensitive")
    private Boolean notSensitive = false;

    @Parameter(names = "-w", description = "Search words only")
    private Boolean wordsOnly = false;

    @Parameter(names = "-A", description = "N lines after the line")
    private Integer linesAfterMatch = 0;

    @Parameter(description = "Pattern")
    private List<String> inputs = new ArrayList<>();

    private Map<String, List<String>> matches = new HashMap<>();
    private Pattern pattern;

    /**
     * Parses input args, builds a pattern based on args.
     * If no other inputs launches grep function what is piped into it,
     * otherwise launches grep function for each file.
     *
     * grep function forms a map of matches which get printed out
     * by print function
     *
     * @throws IOException could throw if file doesn't exist or too few args are supplied
     */
    @Override
    public void run() throws IOException {
        new JCommander(this, args.toArray(new String[0]));
        if (inputs.size() < 1)
            throw new TooFewArgumentsException();

        buildPattern();

        // Launch grep command on each file
        if (inputs.size() == 1) {
            grep("", getInputStream());
        } else {
            for (int i = 1; i < inputs.size(); ++i) {
                String f = inputs.get(i);
                grep(f + ": ", new FileInputStream(new File(f).getAbsoluteFile()));
            }
        }

        print();
    }

    /**
     * Output results, if passed files output filename also
     *
     * @throws IOException may throw
     */
    private void print() throws IOException {
        for (String fileName : matches.keySet()) {
            for (String line : matches.get(fileName)) {
                getOutputStream().write((fileName + line + "\n").getBytes());
            }
        }
    }


    /**
     * While there are lines in the input match those lines
     *
     * If we've got a match auto match next "linesAfterMatch" lines
     *
     * @param filePrefix files prefix for the map
     * @param input input stream from which to match
     * @throws IOException may throw if the stream provided is in an incorrect state
     */
    private void grep(String filePrefix, InputStream input) throws IOException {
        BufferedReader it = new BufferedReader(new InputStreamReader(input));
        if (!matches.containsKey(filePrefix)) {
            matches.put(filePrefix, new ArrayList<>());
        }
        List<String> res = matches.get(filePrefix);

        String line;
        int linesThatGetAFreePass = 0;
        while ((line = it.readLine()) != null) {
            if (match(line)) {
                linesThatGetAFreePass = linesAfterMatch - 1;
                res.add(line);
            } else {
                if (linesThatGetAFreePass > 0){
                    res.add(line);
                    --linesThatGetAFreePass;
                }
            }
        }
    }

    /**
     * Build a pattern according to flags passed
     *
     */
    private void buildPattern(){
        String p = inputs.get(0);
        if (wordsOnly) {
            p = "(\\s|^)" + p + "(\\s|$)";
        }
        if (notSensitive) {
            pattern = Pattern.compile(p, Pattern.CASE_INSENSITIVE);
        } else {
            pattern = Pattern.compile(p);
        }
    }

    /**
     * Try to match the line, return bool on that
     *
     * @param input input line
     * @return if matched
     */
    private boolean match(String input) {
        Matcher m = pattern.matcher(input);
        return m.find();
    }
}
