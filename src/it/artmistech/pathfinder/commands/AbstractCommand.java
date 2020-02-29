package it.artmistech.pathfinder.commands;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.enums.SenderEnum;
import it.artmistech.pathfinder.interfaces.GetConfig;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.List;

public abstract class AbstractCommand implements CommandExecutor, GetConfig {
    private final SenderEnum sender;
    private final PathFinder pathFinder;
    private final String command;

    /**
     * Default constructor
     * @param sender
     * @param pathFinder
     */
    public AbstractCommand(SenderEnum sender, PathFinder pathFinder, String command) {
        this.sender = sender;
        this.pathFinder = pathFinder;
        this.command = command;
        registerCommand();
    }

    private void registerCommand() {
        pathFinder.getCommand(command).setExecutor(this);
    }

    /**
     * Get a string in default config
     * @param path
     * @return String
     */
    @Override
    public String configString(String path) {
        if(!pathFinder.getConfig().isSet(path)) {
            try {
                throw new Exception("No string detected at " + path + " config path");
            }catch (Exception e) {
                Bukkit.getLogger().severe(e.getMessage());
            }
        }
        return pathFinder.getConfig().getString(path);
    }

    /**
     * Get a string in default config
     * @param path
     * @return List of string
     */
    @Override
    public List<String> configStringList(String path) {
        if(!pathFinder.getConfig().isSet(path)) {
            try {
                throw new Exception("No list detected at " + path + " config path");
            }catch (Exception e) {
                Bukkit.getLogger().severe(e.getMessage());
            }
        }
        return pathFinder.getConfig().getStringList(path);
    }

    /**
     * Get a int in default config
     * @param path
     * @return Integer
     */
    @Override
    public int configInt(String path) {
        if(!pathFinder.getConfig().isSet(path)) {
            try {
                throw new Exception("No int detected at " + path + " config path");
            }catch (Exception e) {
                Bukkit.getLogger().severe(e.getMessage());
            }
        }
        return pathFinder.getConfig().getInt(path);
    }

    /**
     * Get a double in default config
     * @param path
     * @return Double
     */
    @Override
    public double configDouble(String path) {
        if(!pathFinder.getConfig().isSet(path)) {
            try {
                throw new Exception("No double detected at " + path + " config path");
            }catch (Exception e) {
                Bukkit.getLogger().severe(e.getMessage());
            }
        }
        return pathFinder.getConfig().getDouble(path);
    }

    /**
     * Get a boolean in default config
     * @param path
     * @return Boolean
     */
    @Override
    public boolean configBoolean(String path) {
        if(!pathFinder.getConfig().isSet(path)) {
            try {
                throw new Exception("No boolean detected at " + path + " config path");
            }catch (Exception e) {
                Bukkit.getLogger().severe(e.getMessage());
            }
        }
        return pathFinder.getConfig().getBoolean(path);
    }

    /**
     * Default checks for all commands
     * @param commandSender
     * @param command
     * @param s
     * @param strings
     * @return Boolean
     */
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof ConsoleCommandSender)) {
            createAccountIfNotExist(commandSender);
        }

        switch (sender) {
            case PLAYER:
                if(commandSender instanceof Player) {
                    execute(commandSender, strings);
                }
                break;
            case CONSOLE:
                if(commandSender instanceof ConsoleCommandSender) {
                    execute(commandSender, strings);
                }
                break;
            case ALL:
                execute(commandSender, strings);
                break;
        }

        return true;
    }

    /**
     * Check variable is set in default config
     * @param path
     * @return
     */
    @Override
    public boolean isSet(String path) {
        return getPathFinder().getConfig().isSet(path);
    }

    /**
     * Default method for commands
     * @param sender
     * @param strings
     */
    public abstract void execute(CommandSender sender, String[] strings);

    public PathFinder getPathFinder() {
        return pathFinder;
    }

    public Economy getEconomy() {
        return pathFinder.getEconomy();
    }

    /**
     * Create a economy account
     * @param sender
     */
    private void createAccountIfNotExist(CommandSender sender) {
        if(!getEconomy().hasAccount(sender.getName())) {
            getEconomy().createPlayerAccount(sender.getName());
        }
    }
}
