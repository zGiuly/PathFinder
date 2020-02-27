package it.artmistech.pathfinder.commands.fun;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.AbstractCommand;
import it.artmistech.pathfinder.enums.SenderEnum;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ThorCommand extends AbstractCommand {
    public ThorCommand(PathFinder pathFinder) {
        super(SenderEnum.PLAYER, pathFinder, "thor");
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        Player player = (Player)sender;

        if(!player.hasPermission("pathfinder.thor")) return;

        Block block = player.getTargetBlock(null, 100);

        block.getWorld().strikeLightning(block.getLocation());
    }
}
