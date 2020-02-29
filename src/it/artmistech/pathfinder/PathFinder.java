package it.artmistech.pathfinder;

import it.artmistech.pathfinder.commands.core.*;
import it.artmistech.pathfinder.commands.fun.KittyCannonCommand;
import it.artmistech.pathfinder.commands.fun.ThorCommand;
import it.artmistech.pathfinder.commands.staff.*;
import it.artmistech.pathfinder.commands.teleport.RandomTpCommand;
import it.artmistech.pathfinder.commands.teleport.TpaCommand;
import it.artmistech.pathfinder.commands.teleport.TpallCommand;
import it.artmistech.pathfinder.commands.teleport.TphereCommand;
import it.artmistech.pathfinder.economy.PathEconomy;
import it.artmistech.pathfinder.listeners.*;
import it.artmistech.pathfinder.io.PluginFile;
import it.artmistech.pathfinder.manager.TpaManager;
import it.artmistech.pathfinder.sqlite.Database;
import it.artmistech.pathfinder.updater.UpdateCheck;
import it.artmistech.pathfinder.utils.DatabaseUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class PathFinder extends JavaPlugin {
    private PluginFile baseConfig;
    private Database defaultDatabase;
    private TpaManager tpaManager;
    private boolean findVault;
    private Economy economy;

    @Override
    public void onEnable() {
        sendConsoleMessage("§aPathFinder " + getDescription().getVersion() + " enabling...");

        updateCheck();
        setupFiles();
        setupCommands();
        setupEvents();
        setupDatabase();
        setupEconomy();


        sendConsoleMessage("§aPathFinder enabled!\n§4Made by Artemide");
    }

    @Override
    public void onDisable() {
        sendConsoleMessage("§aPathFinder save all configs...");

        saveAllConfig();

        sendConsoleMessage("§aPathFinder all saved..");
    }


    private void setupFiles() {
        baseConfig = new PluginFile("config.yml", this, "config.yml");
    }

    private void setupDatabase() {
        try {
            defaultDatabase = new Database(getDataFolder(), "defaultData.db");
            DatabaseUtils.createDefaultNicknameTable(defaultDatabase);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    private void setupEconomy() {
        findVault = false;

        Plugin vault = Bukkit.getPluginManager().getPlugin("Vault");

        if (vault == null) {
            sendConsoleMessage("Vault not found");
        } else {
            findVault = true;

            if (!getConfig().getBoolean("economy.use-economy")) {
                RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);

                if (rsp == null) {
                    sendConsoleMessage("Economy not found, use plugin economy");

                    PathEconomy pathEconomy = new PathEconomy();

                    Bukkit.getServicesManager().register(Economy.class, pathEconomy, vault, ServicePriority.Normal);

                    economy = pathEconomy;
                    return;
                }

                economy = rsp.getProvider();
            } else {
                Bukkit.getServicesManager().register(Economy.class, new PathEconomy(), vault, ServicePriority.Normal);
            }
        }
    }

    private void setupCommands() {
        tpaManager = new TpaManager(this);

        new AfkCommand(this);
        new ClearCommand(this);
        new GamemodeCommand(this);
        new KillAllCommand(this);
        new ReloadCommand(this);
        new RulesCommand(this);
        new SetSpawnCommand(this);
        new SetWarpCommand(this);
        new SpawnCommand(this);
        new WarpCommand(this);
        new WarpsCommand(this);
        new KittyCannonCommand(this);
        new ThorCommand(this);
        new CommandSpy(this);
        new FinishCommand(this);
        new FollowCommand(this);
        new FreezeCommand(this);
        new GodModeCommand(this);
        new SeeCommand(this);
        new StaffChatCommand(this);
        new VanishCommand(this);
        new TpaCommand(this);
        new TpallCommand(this);
        new TphereCommand(this);
        new RandomTpCommand(this);
        new NickCommand(this);
    }

    private void setupEvents() {
        new AfkPlayerListener(this);
        new CommandSpyListener(this);
        new ControlledPlayerListener(this);
        new CreativeDamageListener(this);
        new OpProtectionListener(this);
        new PlayerDisconnectListener(this);
        new PlayerEjectListener(this);
        new SeeInventoryInteract(this);
        new SignChangeListener(this);
        new SpawnListener(this);
        new TagUserListener(this);
        new VanishJoinListener(this);
        new WelcomeListener(this);
        new GodModeListener(this);
        new PlayerJoinListener(this);
    }

    public void saveAllConfig() {
        Bukkit.getPluginManager().disablePlugin(this);
        Bukkit.getPluginManager().enablePlugin(this);
    }

    private void updateCheck() {
        new UpdateCheck(this, 75513).isOutdated();
    }

    @Override
    public FileConfiguration getConfig() {
        return baseConfig;
    }

    public PluginFile getBaseConfig() {
        return baseConfig;
    }

    private void sendConsoleMessage(String data) {
        Bukkit.getConsoleSender().sendMessage(data);
    }

    public Database getDefaultDatabase() {
        return defaultDatabase;
    }

    @Deprecated
    public TpaManager getTpaManager() {
        return tpaManager;
    }

    public Economy getEconomy() {
        return economy;
    }
}
