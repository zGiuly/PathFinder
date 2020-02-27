package it.artmistech.pathfinder.commands.core;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.AbstractCommand;
import it.artmistech.pathfinder.enums.SenderEnum;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand extends AbstractCommand {
    public ReloadCommand(PathFinder pathFinder) {
        super(SenderEnum.PLAYER, pathFinder, "pfreload");
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        if(sender instanceof Player) {
            Player player = (Player)sender;

            if(!player.hasPermission("pathfinder.reload")) return;

            getPathFinder().saveAllConfig();

            player.sendMessage("§aAll config saved and reloaded!");
        }
    }
}
