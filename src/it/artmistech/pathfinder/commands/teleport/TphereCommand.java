package it.artmistech.pathfinder.commands.teleport;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.AbstractCommand;
import it.artmistech.pathfinder.enums.SenderEnum;
import it.artmistech.pathfinder.types.CustomLocation;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TphereCommand extends AbstractCommand {
    public TphereCommand(PathFinder pathFinder) {
        super(SenderEnum.PLAYER, pathFinder, "tphere");
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        Player player = (Player)sender;

        if(!player.hasPermission("pathfinder.tphere")) return;

        CustomLocation playerLocation = CustomLocation.fromLocation(player.getLocation());

        if(strings.length == 1) {
            Player target = Bukkit.getPlayerExact(strings[0]);

            if(target == null || !target.isOnline()) {
                player.sendMessage("§4Player offline");
                return;
            }

            if(strings[0].equals(player.getName())) {
                player.sendMessage("§4You can't teleport");
                return;
            }

            if(!playerLocation.isSafe()) {
                player.sendMessage("§4Location is unsafe!");
                return;
            }

            target.teleport(playerLocation);
            player.sendMessage("§aTeleported!");
            target.sendMessage("§aTeleported to " + player.getName());
        } else {
            player.sendMessage("§4Player not found!");
        }
    }
}
