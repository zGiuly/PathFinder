package it.artmistech.pathfinder.commands.teleport;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.AbstractCommand;
import it.artmistech.pathfinder.enums.SenderEnum;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpaDenyCommand extends AbstractCommand {
    public TpaDenyCommand(PathFinder pathFinder) {
        super(SenderEnum.PLAYER, pathFinder, "tpadeny");
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        if (!sender.hasPermission("pathfinder.deny")) return;

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

        target.sendMessage("§cTpa denied");
        player.sendMessage("§aTpa denied");

        TpaCommand.getInProgress().remove(target.getName());
        TpaCommand.getCooldown().remove(target.getName());
    }
}
