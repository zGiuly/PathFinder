package it.artmistech.pathfinder.listeners;

import it.artmistech.pathfinder.PathFinder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Random;

public class StaffModeListener extends AbstractListener {
    public StaffModeListener(PathFinder pathFinder) {
        super(pathFinder);
    }

    @EventHandler
    public void randomTpInteract(PlayerInteractEvent event) {
        if(event.getItem().getItemMeta().getDisplayName().contains("Random-TP")) {
            Player[] players = (Player[])Bukkit.getOnlinePlayers().toArray();

            Player target = players[new Random().nextInt(players.length)];

            event.getPlayer().teleport(target.getLocation());
        }
    }
}
