package it.artmistech.pathfinder.listeners;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.staff.FollowCommand;
import it.artmistech.pathfinder.commands.staff.FreezeCommand;
import it.artmistech.pathfinder.utils.FreezeUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
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

    @EventHandler
    public void freezeItemInteract(PlayerInteractEvent event) {
        if(event.getItem().getItemMeta().getDisplayName().contains("Freeze")) {
            for (Entity nearbyEntity : event.getPlayer().getNearbyEntities(5, 5, 5)) {
                if(nearbyEntity instanceof Player) {
                    Player player = (Player)nearbyEntity;
                    FreezeUtils.blockUser(player.getName(), event.getPlayer().getName());
                    return;
                }
            }
        }
    }

    @EventHandler
    public void followItemInteract(PlayerInteractEvent event) {
        if(event.getItem().getItemMeta().getDisplayName().contains("Follow")) {
            for (Entity nearbyEntity : event.getPlayer().getNearbyEntities(5, 5, 5)) {
                if(nearbyEntity instanceof Player) {
                    Player player = (Player)nearbyEntity;
                    player.addPassenger(event.getPlayer());
                    FollowCommand.getPassengers().put(event.getPlayer().getName(), player.getName());
                    return;
                }
            }
        }
    }
}
