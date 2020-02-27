package it.artmistech.pathfinder.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginFile extends YamlConfiguration {

    private final File file;
    private final String defaults;
    private final JavaPlugin plugin;

    public PluginFile(String fileName, JavaPlugin plugin) {
        this(fileName, plugin, null);
    }


    public PluginFile(String fileName, JavaPlugin plugin, String defaultsName) {
        this.plugin = plugin;
        this.defaults = defaultsName;
        this.file = new File(plugin.getDataFolder(), fileName);
        reload();
    }

    public Location getLocation(String path) {
        return new Location(Bukkit.getWorld(getString(path+".world")),
                getInt(path+".x"),
                getInt(path+".y"),
                getInt(path+".z"));
    }

    public void setLocation(String path, Location location) {
        set(path+".x", location.getX());
        set(path+".y", location.getY());
        set(path+".z", location.getZ());
        set(path+".yaw", location.getYaw());
        set(path+".world", location.getWorld().getName());

        save();
        reload();
    }

    @Override
    public String getString(String path) {
        return ChatColor.translateAlternateColorCodes('&', super.getString(path));
    }

    public void reload() {

        if (!file.exists()) {

            try {
                file.getParentFile().mkdirs();
                file.createNewFile();

            } catch (IOException e) {
                plugin.getLogger().severe("Error while creating file " + file.getName() + "\n\n"+e);
            }

        }

        try {
            load(file);

            if (defaults != null) {
                InputStreamReader reader = new InputStreamReader(plugin.getResource(defaults));
                FileConfiguration defaultsConfig = YamlConfiguration.loadConfiguration(reader);

                setDefaults(defaultsConfig);
                options().copyDefaults(true);
                options().copyHeader(true);

                reader.close();
                save();
            }

        } catch (IOException | InvalidConfigurationException e) {
            plugin.getLogger().severe("Error while loading file " + file.getName() + "\n\n"+e);

        }

    }

    public void save() {

        try {
            options().indent(2);
            save(file);

        } catch (IOException e) {
            plugin.getLogger().severe("Error while saving file " + file.getName() + "\n\n"+e);
        }

    }

}
