package it.artmistech.pathfinder.commands.teleport;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.AbstractCommand;
import it.artmistech.pathfinder.enums.SenderEnum;
import it.artmistech.pathfinder.types.CustomLocation;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Consumer;

import java.util.ArrayList;
import java.util.List;

public class RandomTpCommand extends AbstractCommand {
    private final List<String> cooldown;

    public RandomTpCommand(PathFinder pathFinder) {
        super(SenderEnum.PLAYER, pathFinder, "randomtp");
        cooldown = new ArrayList<>();
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        Player player = (Player) sender;

        if (!player.hasPermission("pathfinder.randomtp")) return;

        int waiting = configInt("random-tp.cooldown") * 20;

        if (cooldown.contains(player.getName())) {
            player.sendMessage("§cYou can't use the command for §e" + waiting / 20 + " §cseconds");
            return;
        }

        int attempts = configInt("random-tp.max-attempts");
        int max = configInt("random-tp.max-range");

        player.sendMessage("§aI'm looking for an area, it could take a while");

        Consumer<CustomLocation> consumer = randomLocation -> {
            if (randomLocation == null) {
                player.sendMessage("§cI could not find a safe place to take you, try again");
                return;
            }

            Bukkit.getScheduler().runTask(getPathFinder(), () -> {
                player.teleport(randomLocation);
            });

            player.sendMessage("§aI teleported you to a random location");
            if (player.hasPermission("pathfinder.randomtp.bypasscooldown")) return;
            cooldown.add(player.getName());

            Bukkit.getScheduler().runTaskLater(getPathFinder(), () -> {
                cooldown.remove(player.getName());
            }, waiting);
        };

        CustomLocation.randomSafeLocation(max, attempts, player.getLocation(), getPathFinder(), consumer);
    }
}
