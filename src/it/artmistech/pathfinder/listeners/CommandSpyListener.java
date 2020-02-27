package it.artmistech.pathfinder.listeners;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.staff.CommandSpy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandSpyListener extends AbstractListener {
    public CommandSpyListener(PathFinder pathFinder) {
        super(pathFinder);
    }

    @EventHandler
    public void spyCommand(PlayerCommandPreprocessEvent event) {
        if(event.getMessage().startsWith("/")) {
            CommandSpy.getActiveUsers().forEach(user -> {
                Player player = Bukkit.getPlayerExact(user);

                if(player == null || !player.isOnline()) {
                    CommandSpy.getActiveUsers().remove(user);
                    return;
                }

                player.sendMessage("§aCommandSpy > " + "§e"+event.getPlayer().getName() + " §a " + event.getMessage());
            });
        }
    }
}
