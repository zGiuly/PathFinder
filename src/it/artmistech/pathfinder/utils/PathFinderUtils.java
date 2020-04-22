package it.artmistech.pathfinder.utils;

import it.artmistech.pathfinder.commands.staff.FreezeCommand;
import it.artmistech.pathfinder.types.CustomLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

public class PathFinderUtils {
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


    public static void blockUser(String name, String controller) {
        FreezeCommand.getFreezedPlayers().put(name, controller);
    }

    public static boolean isBlocked(String name) {
        return FreezeCommand.getFreezedPlayers().containsKey(name);
    }

    public static void unblockUser(String name) {
        FreezeCommand.getFreezedPlayers().remove(name);
    }


    /**
     * Send packet
     * @param player
     * @param packet
     */
    public static void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * default NMS
     * @param name
     * @return
     */
    public static Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server."
                    + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Send title to player
     * @param player
     * @param title
     * @param subtitle
     * @param fadeInTime
     * @param showTime
     * @param fadeOutTime
     */
    public static void send(Player player, String title, String subtitle, int fadeInTime, int showTime, int fadeOutTime) {
        try {
            Object chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class)
                    .invoke(null, "{\"text\": \"" + title + "\"}");
            Constructor<?> titleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(
                    getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"),
                    int.class, int.class, int.class);
            Object packet = titleConstructor.newInstance(
                    getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null), chatTitle,
                    fadeInTime, showTime, fadeOutTime);

            Object chatsTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class)
                    .invoke(null, "{\"text\": \"" + subtitle + "\"}");
            Constructor<?> timingTitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(
                    getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"),
                    int.class, int.class, int.class);
            Object timingPacket = timingTitleConstructor.newInstance(
                    getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null), chatsTitle,
                    fadeInTime, showTime, fadeOutTime);

            sendPacket(player, packet);
            sendPacket(player, timingPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
