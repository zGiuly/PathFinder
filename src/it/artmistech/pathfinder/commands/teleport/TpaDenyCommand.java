package it.artmistech.pathfinder.commands.teleport;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.AbstractCommand;
import it.artmistech.pathfinder.enums.SenderEnum;
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
    }
}
