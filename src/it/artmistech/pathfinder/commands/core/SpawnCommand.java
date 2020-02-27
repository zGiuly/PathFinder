package it.artmistech.pathfinder.commands.core;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.AbstractCommand;
import it.artmistech.pathfinder.enums.SenderEnum;
import it.artmistech.pathfinder.types.CustomLocation;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand extends AbstractCommand {
    public SpawnCommand(PathFinder pathFinder) {
        super(SenderEnum.ALL, pathFinder, "spawn");
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        CustomLocation spawnLocation = null;

        World world = Bukkit.getWorld(configString("spawn.world"));
        double x = configDouble("spawn.x");
        double y = configDouble("spawn.y");
        double z = configDouble("spawn.z");
        float yaw = configInt("spawn.yaw");

        if(world == null) {
            sender.sendMessage("§cNo spawn set!");
            return;
        }

        spawnLocation = new CustomLocation(world, x, y, z, yaw, 0);

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("pathfinder.spawn")) return;

            if (!spawnLocation.isSafe()) {
                player.sendMessage("§4Spawn location is unsafe!");
                return;
            }

            if (strings.length == 1) {
                Player target = Bukkit.getPlayerExact(strings[0]);

                if (target != null && target.isOnline()) {
                    target.teleport(spawnLocation);
                    player.sendMessage("§aPlayer teleported!");
                    target.sendMessage("§aTeleported to spawn by §e" + player.getName());
                    return;
                }
            }
            player.teleport(spawnLocation);
        } else if (sender instanceof ConsoleCommandSender) {
            if (strings.length != 1) {
                sender.sendMessage("§4No player detected!");
                return;
            }



            Player target = Bukkit.getPlayerExact(strings[0]);

            if (target != null && target.isOnline()) {
                if (!spawnLocation.isSafe()) {
                    sender.sendMessage("§4Spawn location is unsafe!");
                    return;
                }

                target.teleport(spawnLocation);
                sender.sendMessage("§aPlayer teleported!");
                target.sendMessage("§aTeleported to spawn by §e" + sender.getName());
                return;
            } else {
                sender.sendMessage("§4The user is offline!");
            }
        }
    }
}
