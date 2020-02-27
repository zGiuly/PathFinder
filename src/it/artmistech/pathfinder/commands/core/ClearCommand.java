package it.artmistech.pathfinder.commands.core;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.AbstractCommand;
import it.artmistech.pathfinder.enums.SenderEnum;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearCommand extends AbstractCommand {
    public ClearCommand(PathFinder pathFinder) {
        super(SenderEnum.PLAYER, pathFinder, "clear");
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        Player player = (Player)sender;

        if(!player.hasPermission("pathfinder.clear")) return;

        if(strings.length == 1 && player.hasPermission("pathfinder.clear.other")) {
            Player target = Bukkit.getPlayerExact(strings[0]);

            if (target == null || !target.isOnline()) {
                player.sendMessage("§cPlayer offline!");
                return;
            }

            target.getInventory().clear();
            target.getInventory().getItemInMainHand().setType(Material.AIR);
            target.updateInventory();

            target.sendMessage("§aInventory cleared by " + player.getName());
            player.sendMessage("§aInventory cleared");
        } else if(strings.length != 1) {
            player.getInventory().clear();
            player.getInventory().getItemInMainHand().setType(Material.AIR);
            player.updateInventory();

            player.sendMessage("§aInventory cleared");
        }
    }
}
