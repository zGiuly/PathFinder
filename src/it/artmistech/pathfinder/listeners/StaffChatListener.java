package it.artmistech.pathfinder.listeners;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.staff.StaffChatCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class StaffChatListener extends AbstractListener {
    public StaffChatListener(PathFinder pathFinder) {
        super(pathFinder);
    }

    @EventHandler
    public void playerChat(AsyncPlayerChatEvent event) {
        for (String s : StaffChatCommand.getStaffChat()) {
            if(event.getPlayer().getName().equals(s)) {
                event.setCancelled(true);

                String format = configString("staff-chat.format")
                        .replaceAll("%p", event.getPlayer().getName())
                        .replaceAll("%text", event.getMessage());


                Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
                    if(onlinePlayer.hasPermission("pathfinder.staffchat")) {
                        onlinePlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', format));
                    }
                });
            }
        }
    }
}
