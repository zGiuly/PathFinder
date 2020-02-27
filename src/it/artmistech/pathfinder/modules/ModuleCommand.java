package it.artmistech.pathfinder.modules;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * For future implementation
 */
public class ModuleCommand implements CommandExecutor {
    private final BaseModule baseModule;

    public ModuleCommand(BaseModule baseModule) {
        this.baseModule = baseModule;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return false;
    }
}
