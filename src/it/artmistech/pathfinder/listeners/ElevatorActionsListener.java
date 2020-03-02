package it.artmistech.pathfinder.listeners;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.types.CustomLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class ElevatorActionsListener extends AbstractListener {
    private final int max_try;

    public ElevatorActionsListener(PathFinder pathFinder) {
        super(pathFinder);
        max_try = 256;
    }

    @EventHandler
    public void jumpOnBlock(PlayerMoveEvent event) {
        if (event.getTo().getY() > event.getFrom().getY() && event.getFrom().subtract(0, 1, 0).getBlock().getType() == Material.REDSTONE_BLOCK) {
            Bukkit.getScheduler().runTaskAsynchronously(getPathFinder(), () -> {
                Block redstone = null;
                Location startLocation = event.getFrom();
                int i = 0;
                while (i != max_try) {
                    startLocation.setY(startLocation.getY() + 1);
                    redstone = startLocation.getBlock();

                    if (redstone.getType() != Material.REDSTONE_BLOCK) {
                        i++;
                    } else {
                        Block finalRedstone = redstone;

                        CustomLocation location = CustomLocation.fromLocation(finalRedstone.getLocation().add(0, 1, 0));

                        if(!location.isSafe()) return;

                        Bukkit.getScheduler().runTask(getPathFinder(), () -> {
                            event.getPlayer().teleport(location);
                        });

                        break;
                    }
                }
            });
        }
    }

    @EventHandler
    public void sneakOnBlock(PlayerToggleSneakEvent event) {

        if (event.isSneaking()) {

            if (event.getPlayer().getLocation().subtract(0, 1, 0).getBlock().getType() != Material.REDSTONE_BLOCK)
                return;

            Bukkit.getScheduler().runTaskAsynchronously(getPathFinder(), () -> {
                Block redstone = null;
                Location startLocation = event.getPlayer().getLocation();
                int i = 0;
                while (i != max_try) {
                    startLocation.setY(startLocation.getY() - 1);
                    redstone = startLocation.getBlock();

                    if (redstone.getType() != Material.REDSTONE_BLOCK || i == 0) {
                        i++;
                    } else {
                        Block finalRedstone = redstone;

                        CustomLocation location = CustomLocation.fromLocation(finalRedstone.getLocation().add(0, 1, 0));

                        if(!location.isSafe()) return;

                        Bukkit.getScheduler().runTask(getPathFinder(), () -> {
                            event.getPlayer().teleport(location);
                        });
                        break;
                    }
                }
            });
        }
    }
}
