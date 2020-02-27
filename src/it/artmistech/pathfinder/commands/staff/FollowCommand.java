package it.artmistech.pathfinder.commands.staff;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.AbstractCommand;
import it.artmistech.pathfinder.enums.SenderEnum;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class FollowCommand extends AbstractCommand {
    private static Map<String, String> passengers;
    public FollowCommand(PathFinder pathFinder) {
        super(SenderEnum.PLAYER, pathFinder, "follow");
        passengers = new HashMap<>();
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        Player player = (Player)sender;

        if(!player.hasPermission("pathfinder.follow")) return;

        if(strings.length != 1) return;

        Player target = Bukkit.getPlayerExact(strings[0]);

        if(target == null || !target.isOnline()) {
            player.sendMessage("§cPlayer offline");
            return;
        }

        if(strings[0].equals(player.getName())) {
            player.sendMessage("§4You can't teleport");
            return;
        }


        if(passengers.get(player.getName()) == null) {
            passengers.put(player.getName(), target.getName());
            target.addPassenger(player);
            player.sendMessage("§aNow you follow §e" + target.getName());
        } else {
            passengers.remove(player.getName());
            target.removePassenger(player);
            player.sendMessage("§aNow you unfollow §e" + target.getName());
        }
    }


    public static Map<String, String> getPassengers() {
        return passengers;
    }
}
