package ru.spbau.mit.Command;

import ru.spbau.mit.Exceptions.CommandCreationException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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

    public static Command createCommand(String a_commandName, String... a_commandArguments) throws CommandCreationException {
        List<Argument> args = Arrays.stream(a_commandArguments).map(ArgumentImpl::new).collect(Collectors.toList());

        Class<? extends Command> commandClass = COMMANDS.getOrDefault(a_commandName, EXTERNAL_COMMAND_CLASS);
        try {
            Constructor<? extends Command> constructor = commandClass.getDeclaredConstructor(List.class);
            if (!commandClass.equals(EXTERNAL_COMMAND_CLASS))
                return constructor.newInstance(args);

            args.add(0, new ArgumentImpl(a_commandName));
            return constructor.newInstance(args);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new CommandCreationException(a_commandName, e);
        }
    }
}
