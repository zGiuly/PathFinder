package it.artmistech.pathfinder.listeners;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.utils.TitleUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class WelcomeListener extends AbstractListener {
    private final String welcomeMessage;
    private final String welcomeTitle;
    private final String welcomeSubtitle;
    private final String globalWelcomeMessage;

    private final boolean globalWelcome;

    public WelcomeListener(PathFinder pathFinder) {
        super(pathFinder);
        welcomeMessage = ChatColor.translateAlternateColorCodes('&', configString("welcome-message"));
        welcomeTitle = ChatColor.translateAlternateColorCodes('&', configString("welcome-title"));
        welcomeSubtitle = ChatColor.translateAlternateColorCodes('&', configString("welcome-subtitle"));
        globalWelcomeMessage = ChatColor.translateAlternateColorCodes('&', configString("global-welcome-message"));

        globalWelcome = configBoolean("global-welcome");
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        if (configBoolean("disable-welcome")) return;
        event.setJoinMessage(null);

        event.getPlayer().sendMessage(welcomeMessage.replaceAll("%p", event.getPlayer().getName()));

        if (!welcomeTitle.isEmpty() && !welcomeSubtitle.isEmpty()) {
            TitleUtils.send(event.getPlayer(), welcomeTitle.replaceAll("%p", event.getPlayer().getName()), welcomeSubtitle.replaceAll("%p", event.getPlayer().getName()), 0, 60, 10);
        }

        if (globalWelcome) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.sendMessage(globalWelcomeMessage.replaceAll("%p", event.getPlayer().getName()));
            }
        }
    }
}
