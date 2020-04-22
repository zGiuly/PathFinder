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
        if (!sender.hasPermission("pathfinder.tpaccept")) return;

        Player player = (Player) sender;

        if (!TpaCommand.getInProgress().containsValue(player.getName())) {
            player.sendMessage("§cYou currently have no request");
            return;
        }

        final String[] executorName = {null};

        TpaCommand.getInProgress().forEach((executor, target) -> {
            if(target.equals(player.getName())) {
                executorName[0] = executor;
            }
        });

        Player target = Bukkit.getPlayerExact(executorName[0]);

        if (target == null || !target.isOnline()) {
            player.sendMessage("§cPlayer offline!");
            return;
        }

        CustomLocation location = CustomLocation.fromLocation(player.getLocation());

        if (!location.isSafe()) {
            player.sendMessage("§cLocation is unsafe");
            target.sendMessage("§cLocation is unsafe");
            return;
        }

        target.teleport(location);

        TpaCommand.getCooldown().remove(target.getName());
        TpaCommand.getInProgress().remove(target.getName());
    }
}
