package it.artmistech.pathfinder.commands.teleport;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.AbstractCommand;
import it.artmistech.pathfinder.enums.SenderEnum;
import it.artmistech.pathfinder.types.CustomLocation;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpaDenyCommand extends AbstractCommand {
    public TpaDenyCommand(PathFinder pathFinder) {
        super(SenderEnum.PLAYER, pathFinder, "tpadeny");
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        if(!sender.hasPermission("pathfinder.deny")) return;

        Player player = (Player)sender;

        if(TpaCommand.getCooldown().get(player.getName()) == 0) {
            player.sendMessage("§cYou currently have no request");
            return;
        }

        Player target = Bukkit.getPlayerExact(TpaCommand.getInProgress().get(player.getName()));

        if (target == null || !target.isOnline()) {
            player.sendMessage("§cPlayer offline!");
            return;
        }

        target.sendMessage("§aTpa denied");
        player.sendMessage("§cTpa denied");

        TpaCommand.getInProgress().remove(player.getName());
        TpaCommand.getCooldown().remove(player.getName());
    }
}
