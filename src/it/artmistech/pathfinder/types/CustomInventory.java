package it.artmistech.pathfinder.types;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class CustomInventory implements InventoryHolder {
    private final Player player;
    private final String title;
    private final Object data;

    public CustomInventory(Player player, String title, Object data) {
        this.player = player;
        this.title = title;
        this.data = data;
    }

    public CustomInventory(Player player, String title) {
        this(player, title, null);
    }

    @Override
    public Inventory getInventory() {
        return player.getInventory();
    }

    public String getTitle() {
        return title;
    }

    public Object getData() {
        return data;
    }
}
