package it.artmistech.pathfinder.utils;

import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.entity.*;

public class KillUtils {
    public static int killAllMonsters(World world) {
        int counter = 0;
        for (Monster entitiesByClass : world.getEntitiesByClass(Monster.class)) {
            entitiesByClass.remove();
            counter++;
        }
        return counter;
    }

    public static int killAllAnimals(World world) {
        int counter = 0;
        for (Animals entitiesByClass : world.getEntitiesByClass(Animals.class)) {
            entitiesByClass.remove();
            counter++;
        }
        return counter;
    }

    public static int killAllEntities(World world) {
        int counter = 0;
        for (Entity entity : world.getEntities()) {
            if (!(entity instanceof Sign) && !(entity instanceof ItemFrame) && !(entity instanceof Player) && !(entity instanceof Minecart)) {
                counter++;
                entity.remove();
            }
        }
        return counter;
    }

    public static int killAllDrops(World world) {
        int counter = 0;
        for (Item entitiesByClass : world.getEntitiesByClass(Item.class)) {
            counter+= ((Item)entitiesByClass).getItemStack().getAmount();
            ((Item)entitiesByClass).remove();
        }
        return counter;
    }
}
