package it.artmistech.pathfinder.commands.teleport;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.AbstractCommand;
import it.artmistech.pathfinder.enums.SenderEnum;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ElevatorCommand extends AbstractCommand {
    private final int max_try;

    public ElevatorCommand(PathFinder pathFinder) {
        super(SenderEnum.PLAYER, pathFinder, "elevator");
        max_try = 256;
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        if (!sender.hasPermission("pathfinder.elevator")) return;
        Player player = (Player) sender;

        if (strings.length == 2) {
            if (strings[0].equalsIgnoreCase("add")) {
                int distance = Integer.parseInt(strings[1]);
                Location loc = player.getLocation();
                Location placeLocation = new Location(loc.getWorld(), loc.getX(), loc.getY() + distance, loc.getZ());

                placeLocation.getBlock().setType(Material.REDSTONE_BLOCK);

                player.sendMessage("§aFloor created");
            } else if (strings[0].equalsIgnoreCase("remove")) {
                if (strings[1].equalsIgnoreCase("up")) {

                    Bukkit.getScheduler().runTaskAsynchronously(getPathFinder(), () -> {
                        Block redstone = null;
                        Location startLocation = player.getLocation();
                        int i = 0;
                        while (i != max_try) {
                            startLocation.setY(startLocation.getY() + 1);
                            redstone = startLocation.getBlock();

                            if (redstone.getType() != Material.REDSTONE_BLOCK) {
                                i++;
                            } else {
                                Block finalRedstone = redstone;
                                Bukkit.getScheduler().runTask(getPathFinder(), () -> {
                                    finalRedstone.setType(Material.AIR);
                                });

                                player.sendMessage("§cFloor removed");
                                break;
                            }
                        }

                    });

                } else if (strings[1].equalsIgnoreCase("down")) {

                    Bukkit.getScheduler().runTaskAsynchronously(getPathFinder(), () -> {

                        Block redstone = null;
                        Location startLocation = player.getLocation();
                        int i = 0;
                        while (i != max_try) {
                            startLocation.setY(startLocation.getY() - 1);
                            redstone = startLocation.getBlock();

                            if (redstone.getType() != Material.REDSTONE_BLOCK) {
                                i++;
                            } else {
                                Block finalRedstone = redstone;
                                Bukkit.getScheduler().runTask(getPathFinder(), () -> {
                                    finalRedstone.setType(Material.AIR);
                                });

                                player.sendMessage("§cFloor removed");
                                break;
                            }
                        }

                    });

                }

            }
        }
    }
}
