package it.artmistech.pathfinder.utils;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.core.IgnoreCommand;
import it.artmistech.pathfinder.commands.staff.FreezeCommand;
import it.artmistech.pathfinder.commands.staff.VanishCommand;
import it.artmistech.pathfinder.sqlite.Database;
import it.artmistech.pathfinder.types.CustomLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;

import javax.annotation.Nonnull;
import java.lang.reflect.Constructor;
import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * Save hasmap in database
     *
     * @param database
     */
    public static void saveOnDatabase(@Nonnull Database database) {
        IgnoreCommand.getIgnoredUsers().forEach((player, ignoredList) -> {
            try (PreparedStatement statement = database.getConnection().prepareStatement("INSERT INTO playerIgnore VALUES (?,?)")) {
                statement.setString(1, player);
                Array array = database.getConnection().createArrayOf("TEXT", ignoredList.toArray());
                statement.setArray(2, array);

                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Get ignored users for player
     *
     * @param database
     * @param name
     * @return
     */
    public static Map<String, List<String>> getIgnoredUsers(@Nonnull Database database, @Nonnull String name) {
        try (PreparedStatement statement = database.getConnection().prepareStatement("SELECT * playerIgnore WHERE name = ?")) {
            ResultSet rs = statement.executeQuery();

            Array sqlArray = null;


            rs.next();
            sqlArray = rs.getArray("ignoredUsers");

            if (sqlArray == null) {
                return null;
            }

            String[] strings = (String[]) sqlArray.getArray();
            Map<String, List<String>> map = new HashMap<>();

            map.put(name, Arrays.asList(strings));

            return map;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get all users in ignored table
     * @param database
     * @return
     */
    public static Map<String, List<String>> getAllUsers(@Nonnull Database database) {
        Map<String, List<String>> map = new HashMap<>();
        try (Statement statement = database.getConnection().createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT * playerIgnore WHERE *");




            while (rs.next()) {
                Array sqlArray = rs.getArray("ignoredUsers");
                String name = rs.getString("name");

                if (sqlArray != null) {
                    String[] strings = (String[]) sqlArray.getArray();

                    map.put(name, Arrays.asList(strings));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * Setup map
     */
    public static void setupHashMap(@Nonnull Database database) {
        if(IgnoreCommand.getIgnoredUsers() == null) return;

        IgnoreCommand.getIgnoredUsers().putAll(getAllUsers(database));
    }

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

    /**
     * Extract real name from database
     * @param database
     * @param name
     */
    public static String extractRealNameFromDatabase(@Nonnull Database database, @Nonnull String name) {
        String query = "SELECT * FROM playerNickname WHERE newname = ?";
        String realname = null;

        try(PreparedStatement statement = database.getConnection().prepareStatement(query)) {
            statement.setString(1, name);

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                realname = resultSet.getString("realname");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return realname;
    }

    /**
     * Extract new name from database
     * @param database
     * @param name
     */
    public static String extractNewNameFromDatabase(@Nonnull Database database, @Nonnull String name) {
        String query = "SELECT * FROM playerNickname WHERE realname = ?";
        String newName = null;

        try(PreparedStatement statement = database.getConnection().prepareStatement(query)) {
            statement.setString(1, name);

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                newName = resultSet.getString("newname");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return newName;
    }

    /**
     * Save name in database
     * @param database
     * @param name
     * @param realname
     */
    public static void saveName(@Nonnull Database database, @Nonnull String name, @Nonnull String realname) {
        String query = "INSERT INTO playerNickname VALUES (?,?)";

        if(extractNewNameFromDatabase(database, realname) == null) {
            try (PreparedStatement statement = database.getConnection().prepareStatement(query)) {
                statement.setString(1, realname);
                statement.setString(2, name);

                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            updateName(database, name, realname);
        }
    }

    /**
     * Update name in database
     * @param database
     * @param name
     * @param realname
     */
    public static void updateName(@Nonnull Database database, @Nonnull String name, @Nonnull String realname) {
        String query = "UPDATE playerNickname SET newname = ? WHERE realname = ?";

        try (PreparedStatement statement = database.getConnection().prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, realname);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete name in database
     * @param database
     * @param name
     */
    public static void removeName(@Nonnull Database database, @Nonnull String name) {
        String query = "DELETE FROM playerNickname WHERE realname = ?";

        try (PreparedStatement statement = database.getConnection().prepareStatement(query)) {
            statement.setString(1, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setInvisible(PathFinder pathFinder, Player player) {
        VanishCommand.getVanishPlayers().add(player.getName());
        player.setInvulnerable(true);
        player.setCanPickupItems(false);

        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            if (!onlinePlayer.hasPermission("pathfinder.see.vanish")) {
                onlinePlayer.hidePlayer(pathFinder, player);
            }
        });
    }


    public static void setVisible(PathFinder pathFinder, Player player) {
        VanishCommand.getVanishPlayers().remove(player.getName());
        player.setInvulnerable(false);
        player.setCanPickupItems(true);

        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.showPlayer(pathFinder, player));
    }
}
