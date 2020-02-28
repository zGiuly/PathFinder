package it.artmistech.pathfinder.utils;

import it.artmistech.pathfinder.types.CustomLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;

public class LocationUtils {
    /**
     * Check the location is safe to teleport
     *
     * @param location
     * @return boolean
     */
    public static boolean isSafeLocation(Location location) {
        try {
            Block feet = location.getBlock();

            if (!feet.getType().isTransparent() && !feet.getLocation().add(0, 1, 0).getBlock().getType().isTransparent()) {
                return false;
            }
            Block head = feet.getRelative(BlockFace.UP);
            if (!head.getType().isTransparent()) {
                return false;
            }


            if (feet.getType() == Material.FIRE) {
                return false;
            }

            if (feet.isLiquid()) {
                return false;
            }

            Block ground = feet.getRelative(BlockFace.DOWN);

            return ground.getType().isSolid();
        } catch (Exception ignored) {
        }
        return true;
    }


    /**
     * Convert location to customlocation
     *
     * @param path
     * @param config
     * @return
     */
    public static CustomLocation extractLocation(String path, FileConfiguration config) {
        return new CustomLocation(
                Bukkit.getWorld(config.getString(path + ".world")),
                config.getDouble(path + ".x"),
                config.getDouble(path + ".y"),
                config.getDouble(path + ".z"),
                (float) config.getInt(path + ".yaw"), 0);
    }
}
