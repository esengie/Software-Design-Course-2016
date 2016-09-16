package ru.spbau.mit.InputProcessing;

import ru.spbau.mit.Command.Command;
import ru.spbau.mit.Command.CommandFactory;
import ru.spbau.mit.Exceptions.CommandCreationException;
import ru.spbau.mit.Exceptions.VariableUndefinedException;
import ru.spbau.mit.ShellEnvironment.ShellEnvironment;
import ru.spbau.mit.Util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public static Command parse(String a_input, ShellEnvironment a_env) throws VariableUndefinedException, CommandCreationException {
        List<String> pipedCommands = processInput(a_input, a_env);
        pipedCommands.forEach(System.out::println);
        return CommandFactory.createCommand("pwd", new ArrayList<>());
    }
}
