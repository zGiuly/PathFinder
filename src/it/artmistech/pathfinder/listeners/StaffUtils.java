package it.artmistech.pathfinder.listeners;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;

public class StaffUtils {
    /**
     * Get staff mode tools
     * @return Return list items
     */
    public static ItemStack[] getStaffItems() {
        ItemStack[] itemStacks = new ItemStack[4];

        ItemStack freezeItem = new ItemStack(Material.BLAZE_ROD);
        ItemStack vanishItem = new ItemStack(Material.MAGMA_CREAM);
        ItemStack followItem = new ItemStack(Material.TRIPWIRE_HOOK);
        ItemStack randomTpItem = new ItemStack(Material.COMPASS);

        ItemMeta freezeItemMeta = freezeItem.getItemMeta();
        ItemMeta vanishItemMeta = vanishItem.getItemMeta();
        ItemMeta followItemMeta = followItem.getItemMeta();
        ItemMeta randomTpItemMeta = randomTpItem.getItemMeta();

        freezeItemMeta.setLore(Collections.singletonList("§a[Right click for freeze]"));
        freezeItemMeta.setDisplayName("§aFreeze");

        freezeItem.setItemMeta(freezeItemMeta);

        vanishItemMeta.setDisplayName("§aVanish");

        vanishItem.setItemMeta(freezeItemMeta);

        followItemMeta.setLore(Collections.singletonList("§a[Right click for follow]"));
        followItemMeta.setDisplayName("§aFollow");

        followItem.setItemMeta(freezeItemMeta);

        randomTpItemMeta.setLore(Collections.singletonList("§a[Right click for Random-TP]"));
        randomTpItemMeta.setDisplayName("§aRandom-TP");

        randomTpItem.setItemMeta(freezeItemMeta);

        itemStacks[0] = freezeItem;
        itemStacks[1] = vanishItem;
        itemStacks[2] = followItem;
        itemStacks[3] = randomTpItem;

        return itemStacks;
    }
}
