package it.artmistech.pathfinder.commands.teleport;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.AbstractCommand;
import it.artmistech.pathfinder.enums.SenderEnum;
import it.artmistech.pathfinder.types.CustomLocation;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpaAcceptCommand extends AbstractCommand {
    public TpaAcceptCommand(PathFinder pathFinder) {
        super(SenderEnum.PLAYER, pathFinder, "tpaccept");
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        if(!sender.hasPermission("pathfinder.tpaccept")) return;

        Player player = (Player)sender;

        if(TpaCommand.getCooldown().get(player.getName()) == 0) {
            player.sendMessage("§cYou currently have no request");
            return;
        }

        Player target = Bukkit.getPlayerExact(TpaCommand.getInProgress().get(player.getName()));

        if(target == null || !target.isOnline()) {
            return;
        }

        CustomLocation location = CustomLocation.fromLocation(target.getLocation());

        if(!location.isSafe()) return;

        player.teleport(location);
    }
}
