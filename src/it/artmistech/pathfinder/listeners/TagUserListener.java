package it.artmistech.pathfinder.listeners;

import it.artmistech.pathfinder.PathFinder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Arrays;
import java.util.StringTokenizer;

public class TagUserListener extends AbstractListener {
    public TagUserListener(PathFinder pathFinder) {
        super(pathFinder);
    }

    @EventHandler
    public void playerTagChat(AsyncPlayerChatEvent event) {
        if(!event.getPlayer().hasPermission("pathfinder.tag")) return;

        String message = event.getMessage();
        String[] data = null;

        if (message.contains("@")) {
            data = message.split("@");

            if (data.length == 0) return;

            String name = data[1];

            if(name.equals(event.getPlayer().getName())) return;

            message = message.replace("@"+name, "");

            Player target = Bukkit.getPlayerExact(name);

            if (target == null || !target.isOnline()) {
                event.getPlayer().sendMessage("§cPlayer offline!");
                event.setCancelled(true);
                return;
            }

            event.setFormat(configString("tag.format")
            .replaceAll("%p", event.getPlayer().getName())
            .replaceAll("%text", message)
            .replaceAll("%tag", "@"+name));

            target.sendMessage(configString("tag.mention-message").replaceAll("%p", event.getPlayer().getName()));
            target.playSound(target.getLocation(), Sound.valueOf(configString("tag.mention-sound")), 5, 5);
        }
    }
}
