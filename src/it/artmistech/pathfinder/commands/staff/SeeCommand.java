package it.artmistech.pathfinder.commands.staff;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.AbstractCommand;
import it.artmistech.pathfinder.enums.SenderEnum;
import it.artmistech.pathfinder.types.CustomInventory;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class SeeCommand extends AbstractCommand {
    public SeeCommand(PathFinder pathFinder) {
        super(SenderEnum.PLAYER, pathFinder, "see");
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        Player player = (Player)sender;

        if(!player.hasPermission("pathfinder.see")) return;

        if(strings.length != 1) return;

        Player target = Bukkit.getPlayerExact(strings[0]);

        if(target == null || !target.isOnline()) {
            player.sendMessage("§cPlayer offline!");
            return;
        }

        if(target.hasPermission("pathfinder.see.ignore")) {
            player.sendMessage("§cYou can't see the inventory of this player");
            return;
        }

        Inventory seeInventory = Bukkit.createInventory(new CustomInventory(player, "Player inventory"), 45, "§cPlayer inventory");
        seeInventory.setContents(player.getInventory().getContents());

        player.sendMessage("§e§lWARNING: §cThis inventory is blocked!");
        player.openInventory(seeInventory);
    }
}
