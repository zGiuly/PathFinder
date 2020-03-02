package it.artmistech.pathfinder.commands.staff;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.AbstractCommand;
import it.artmistech.pathfinder.enums.SenderEnum;
import it.artmistech.pathfinder.listeners.StaffUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.Map;

public class StaffModeCommand extends AbstractCommand {
    private final Map<String, PlayerInventory> playerInventory;
    public StaffModeCommand(PathFinder pathFinder) {
        super(SenderEnum.PLAYER, pathFinder, "staffmode");
        playerInventory = new HashMap<>();
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        if(!sender.hasPermission("pathfinder.staffmode")) return;

        Player player = (Player)sender;

        if(!playerInventory.containsKey(player.getName())) {
            player.getInventory().setContents(playerInventory.get(player.getName()).getContents());
            playerInventory.remove(player.getName());
        } else {
            playerInventory.put(player.getName(), player.getInventory());
            player.getInventory().clear();

            for (ItemStack staffItem : StaffUtils.getStaffItems()) {
                player.getInventory().addItem(staffItem);
            }
        }
    }
}
