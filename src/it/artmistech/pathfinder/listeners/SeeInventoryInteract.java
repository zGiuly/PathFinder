package it.artmistech.pathfinder.listeners;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.types.CustomInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

public class SeeInventoryInteract extends AbstractListener {
    public SeeInventoryInteract(PathFinder pathFinder) {
        super(pathFinder);
    }

    @EventHandler
    public void interactSeeInventory(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if(!(event.getInventory().getHolder() instanceof CustomInventory)) return;

        CustomInventory inventory = (CustomInventory)event.getInventory().getHolder();

        if(!inventory.getTitle().equals("Player inventory")) return;

        if (event.getCurrentItem() == null) return;

        if (!player.hasPermission("pathfinder.see")) {
            player.closeInventory();
        }
        event.setCancelled(true);
    }
}
