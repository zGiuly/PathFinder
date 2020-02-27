package it.artmistech.pathfinder.listeners;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.interfaces.GetConfig;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.util.List;

public abstract class AbstractListener implements Listener, GetConfig {
    private final PathFinder pathFinder;

    /**
     * Default constructor for all listeners
     * @param pathFinder
     */
    public AbstractListener(PathFinder pathFinder) {
        this.pathFinder = pathFinder;
        registerListener();
    }

    private void registerListener() {
        Bukkit.getPluginManager().registerEvents(this, getPathFinder());
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
     * Check variable is set in default config
     * @param path
     * @return
     */
    @Override
    public boolean isSet(String path) {
        return getPathFinder().getConfig().isSet(path);
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


    public PathFinder getPathFinder() {
        return pathFinder;
    }
}
