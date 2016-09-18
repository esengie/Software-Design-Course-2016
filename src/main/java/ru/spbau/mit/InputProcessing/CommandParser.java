package ru.spbau.mit.InputProcessing;

import ru.spbau.mit.Command.Command;
import ru.spbau.mit.Command.CommandFactory;
import ru.spbau.mit.Exceptions.CommandCreationException;
import ru.spbau.mit.Exceptions.VariableUndefinedException;
import ru.spbau.mit.Piping.Pipe;
import ru.spbau.mit.ShellEnvironment.ShellEnvironment;
import ru.spbau.mit.Util.Pair;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class CommandParser {
    public static List<String> processInput(String a_input, ShellEnvironment a_env) throws VariableUndefinedException {
        List<String> piped = CommandSplitter.splitByPipe(a_input);
        for (int i = 0; i < piped.size();){
            Optional<Pair<String, String>> res =
                    EnvironmentReader.getEnvironmentVariable(piped.get(i));
            if (!res.isPresent()){
                piped.set(i,
                        VariableSubstituter.substituteVariables(piped.get(i), a_env));
                ++i;
                continue;
            }

            a_env.addToEnvironment(res.get().first, res.get().second);
            piped.remove(i);
        }

        return piped;
    }

    public static Command parse(String a_input, ShellEnvironment a_env) throws VariableUndefinedException, CommandCreationException, IOException {
        List<String> pipedCommands = processInput(a_input, a_env);
        Deque<Command> commands = new ArrayDeque<>();
        for (String s : pipedCommands){
            commands.add(formCommand(s));
        }

        while (commands.size() > 1){
            Command left = commands.pollFirst();
            Command right = commands.pollFirst();
            commands.addFirst(Pipe.connect(left, right));
        }

        return commands.pollFirst();
    }

    private static Command formCommand(String s) throws CommandCreationException {
        String[] l = s.split("\\s");
        if (l.length < 2)
            return CommandFactory.createCommand(s);
        return CommandFactory.createCommand(l[0], Arrays.copyOfRange(l, 1, l.length));
    }
}
