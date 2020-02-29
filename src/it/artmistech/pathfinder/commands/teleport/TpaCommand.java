package it.artmistech.pathfinder.commands.teleport;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.AbstractCommand;
import it.artmistech.pathfinder.enums.SenderEnum;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpaCommand extends AbstractCommand {
    public TpaCommand(PathFinder pathFinder) {
        super(SenderEnum.PLAYER, pathFinder, "tpa");
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        Player player = (Player) sender;
        if (!player.hasPermission("pathfinder.tpa")) return;

        //Work in progress
    }
}
