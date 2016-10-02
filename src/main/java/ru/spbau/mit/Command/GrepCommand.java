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
     * Outputs its own args to outputstream
     *
     * @throws IOException could throw
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

        // Output results, if passed files output filename also
        for (String fileName : matches.keySet()) {
            for (String line : matches.get(fileName)) {
                getOutputStream().write((fileName + line + "\n").getBytes());
            }
        }

    }

    private void grep(String filePrefix, InputStream input) throws IOException {
        BufferedReader it = new BufferedReader(new InputStreamReader(input));
        if (!matches.containsKey(filePrefix))
            matches.put(filePrefix, new ArrayList<>());
        List<String> res = matches.get(filePrefix);

        String line;
        int linesThatGetAFreePass = 0;
        while ((line = it.readLine()) != null) {
            if ((linesThatGetAFreePass > 0) || match(line)) {
                linesThatGetAFreePass = linesAfterMatch;
                res.add(line);
            }
            if (!match(line)){
                linesThatGetAFreePass = Math.max(linesThatGetAFreePass - 1, 0);
            }
        }
    }

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

    private boolean match(String input) {
        Matcher m = pattern.matcher(input);
        return m.find();
    }
}
