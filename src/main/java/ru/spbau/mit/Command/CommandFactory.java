package ru.spbau.mit.Command;

import ru.spbau.mit.Exceptions.CommandCreationError;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Command factory -- use this to build your shell commands from this package
 */
public class CommandFactory {
    private static final Class<ExternalCommand> EXTERNAL_COMMAND_CLASS = ExternalCommand.class;
    private static final Map<String, Class<? extends Command>> COMMANDS = new ConcurrentHashMap<>();

    static {
        COMMANDS.put("exit", ExitCommand.class);
        COMMANDS.put("cat", CatCommand.class);
        COMMANDS.put("pwd", PwdCommand.class);
        COMMANDS.put("wc", WcCommand.class);
        COMMANDS.put("echo", EchoCommand.class);
    }

    /**
     * A command factory create method - gets the command name and its parameters
     * and returns a specified command or an external command if
     * the command name wasn't recognized
     *
     * @param a_commandName Command name
     * @param a_commandArguments Command args
     * @return Command interface implementor
     */
    public static Command createCommand(String a_commandName, String... a_commandArguments) {
        List<Argument> args = Arrays.stream(a_commandArguments).map(ArgumentImpl::new).collect(Collectors.toList());

        Class<? extends Command> commandClass = COMMANDS.getOrDefault(a_commandName, EXTERNAL_COMMAND_CLASS);
        try {
            Constructor<? extends Command> constructor = commandClass.getDeclaredConstructor(List.class);
            if (!commandClass.equals(EXTERNAL_COMMAND_CLASS))
                return constructor.newInstance(args);

            args.add(0, new ArgumentImpl(a_commandName));
            return constructor.newInstance(args);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new CommandCreationError(a_commandName, e);
        }
    }
}
