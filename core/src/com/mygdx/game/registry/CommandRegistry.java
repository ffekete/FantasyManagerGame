package com.mygdx.game.registry;

import com.mygdx.game.logic.command.Command;

import java.util.ArrayList;
import java.util.List;

public class CommandRegistry {

    public static final CommandRegistry INSTANCE = new CommandRegistry();

    private final List<Command> commands;

    private CommandRegistry() {
        commands = new ArrayList<>();
    }

    public List<Command> getCommands() {
        return commands;
    }

    public void add(Command command) {
        commands.add(command);
    }

    public void remove(Command command) {
        commands.remove(command);
    }
}
