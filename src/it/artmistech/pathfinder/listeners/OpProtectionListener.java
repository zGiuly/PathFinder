package it.artmistech.pathfinder.listeners;

import it.artmistech.pathfinder.PathFinder;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class OpProtectionListener extends AbstractListener {
    public OpProtectionListener(PathFinder pathFinder) {
        super(pathFinder);
    }

    @EventHandler
    public void opChat(AsyncPlayerChatEvent event) {
        if(!event.getPlayer().isOp()) return;

        boolean found = false;

        for (String s : configStringList("op-list")) {
            if(s.equals(event.getPlayer().getName())) {
                found = true;
                break;
            }
        }

        if(!found) {
            Bukkit.getScheduler().runTask(getPathFinder(), () -> {
               event.getPlayer().setOp(false);
               event.getPlayer().sendMessage("§cOp protection disable your op");
            });
        }
    }


    @EventHandler
    public void opCommand(PlayerCommandPreprocessEvent event) {
        if(!event.getPlayer().isOp()) return;

        boolean found = false;

        for (String s : configStringList("op-list")) {
            if(s.equals(event.getPlayer().getName())) {
                found = true;
                break;
            }
        }

        if(!found) {
            event.setCancelled(true);
            event.getPlayer().setOp(false);
            event.getPlayer().sendMessage("§cOp protection disable your op");
        }
    }
}
