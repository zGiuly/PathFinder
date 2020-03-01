package it.artmistech.pathfinder.listeners;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.core.ItemBlacklistCommand;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPickupItemEvent;

import java.util.List;

public class ItemBlacklistListener extends AbstractListener {
    public ItemBlacklistListener(PathFinder pathFinder) {
        super(pathFinder);
    }

    @EventHandler
    public void blacklistedItemPickup(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        Player player = (Player) event.getEntity();

        if (!player.hasPermission("pathfinder.pickup")) {
            ItemBlacklistCommand.getBlockBlacklist().remove(player.getName());
        }

        if (!ItemBlacklistCommand.getBlockBlacklist().containsKey(player.getName())) return;

        List<String> data = ItemBlacklistCommand.getBlockBlacklist().get(player.getName());

        for (String datum : data) {
            if (event.getItem().getItemStack().getType() == Material.valueOf(datum)) {
                event.setCancelled(true);
                break;
            }
        }
    }
}
