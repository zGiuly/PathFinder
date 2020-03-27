package it.artmistech.pathfinder;

import it.artmistech.pathfinder.commands.core.*;
import it.artmistech.pathfinder.commands.economy.BalanceCommand;
import it.artmistech.pathfinder.commands.economy.EconomyCommand;
import it.artmistech.pathfinder.commands.fun.HumanSpiderCommand;
import it.artmistech.pathfinder.commands.fun.KittyCannonCommand;
import it.artmistech.pathfinder.commands.fun.NukeCommand;
import it.artmistech.pathfinder.commands.fun.ThorCommand;
import it.artmistech.pathfinder.commands.staff.*;
import it.artmistech.pathfinder.commands.teleport.*;
import it.artmistech.pathfinder.economy.PathEconomy;
import it.artmistech.pathfinder.listeners.*;
import it.artmistech.pathfinder.io.PluginFile;
import it.artmistech.pathfinder.sqlite.Database;
import it.artmistech.pathfinder.updater.UpdateCheck;
import it.artmistech.pathfinder.utils.DatabaseUtils;
import it.artmistech.pathfinder.utils.IgnoredUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public class PathFinder extends JavaPlugin {
    private PluginFile baseConfig;
    private Database defaultDatabase;
    private Economy economy;

    @Override
    public void onEnable() {
        updateCheck();
        setupFiles();
        setupCommands();
        setupEvents();
        setupDatabase();
        setupEconomy();
        //setupIgnoredUsers();
    }

    @Override
    public void onDisable() {
        baseConfig.save();
    }


    private void setupFiles() {
        baseConfig = new PluginFile("config.yml", this, "config.yml");
    }

    private void setupDatabase() {
        try {
            defaultDatabase = new Database(getDataFolder(), "defaultData.db");
            DatabaseUtils.createDefaultNicknameTable(defaultDatabase);
            DatabaseUtils.createDefaultEconomyTable(defaultDatabase);
            DatabaseUtils.createDefaultIgnoreTable(defaultDatabase);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    /*
    private void setupIgnoredUsers() {
        IgnoredUtils.setupHashMap(defaultDatabase);
    }
    */

    private void setupEconomy() {
        Plugin vault = Bukkit.getPluginManager().getPlugin("Vault");

        if (vault == null) {
            sendConsoleMessage("Vault not found");
        } else {
            PathEconomy pathEconomy = new PathEconomy(defaultDatabase);

            if (!getConfig().getBoolean("economy.use-economy")) {
                RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);

                if (rsp == null) {
                    sendConsoleMessage("Economy not found, use plugin economy");

                    Bukkit.getServicesManager().register(Economy.class, pathEconomy, vault, ServicePriority.Normal);

                    economy = pathEconomy;
                    return;
                }
                economy = rsp.getProvider();
            } else {
                Bukkit.getServicesManager().register(Economy.class, pathEconomy, vault, ServicePriority.Normal);
                economy = pathEconomy;
            }
        }
    }

    private void setupCommands() {
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
        new BalanceCommand(this);
        new EconomyCommand(this);
        new ItemBlacklistCommand(this);
        new TpaAcceptCommand(this);
        new TpaDenyCommand(this);
        new NukeCommand(this);
        new ElevatorCommand(this);
        new HumanSpiderCommand(this);
        new StaffChatCommand(this);
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
        new ItemBlacklistListener(this);
        new NukeProtectionListener(this);
        new ElevatorActionsListener(this);
        new SpiderControlListener(this);
        new StaffChatListener(this);
        new StaffModeListener(this);
    }

    public void saveAll() {
        //IgnoredUtils.saveOnDatabase(defaultDatabase);

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

    public Economy getEconomy() {
        return economy;
    }
}
