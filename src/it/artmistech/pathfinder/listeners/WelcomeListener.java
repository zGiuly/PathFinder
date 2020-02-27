package it.artmistech.pathfinder.listeners;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.utils.TitleUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class WelcomeListener extends AbstractListener {
    public WelcomeListener(PathFinder pathFinder) {
        super(pathFinder);
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        if (configBoolean("disable-welcome")) return;
        event.setJoinMessage(null);

        String welcomeMessage = ChatColor.translateAlternateColorCodes('&', configString("welcome-message")).replaceAll("%p", event.getPlayer().getName());
        String welcomeTitle = ChatColor.translateAlternateColorCodes('&', configString("welcome-title")).replaceAll("%p", event.getPlayer().getName());
        String welcomeSubtitle = ChatColor.translateAlternateColorCodes('&', configString("welcome-subtitle")).replaceAll("%p", event.getPlayer().getName());
        String globalWelcomeMessage = ChatColor.translateAlternateColorCodes('&', configString("global-welcome-message")).replaceAll("%p", event.getPlayer().getName());

        boolean globalWelcome = configBoolean("global-welcome");

        event.getPlayer().sendMessage(welcomeMessage);

        if(!welcomeTitle.isEmpty() && !welcomeSubtitle.isEmpty()) {
            TitleUtils.send(event.getPlayer(), welcomeTitle, welcomeSubtitle, 0, 60, 10);
        }

        if(globalWelcome) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.sendMessage(globalWelcomeMessage);
            }
        }
    }
}
