package it.artmistech.pathfinder.utils;

import it.artmistech.pathfinder.commands.core.IgnoreCommand;
import it.artmistech.pathfinder.sqlite.Database;

import javax.annotation.Nonnull;
import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IgnoredUtils {
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
}
