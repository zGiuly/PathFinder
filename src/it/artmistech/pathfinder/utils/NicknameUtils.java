package it.artmistech.pathfinder.utils;

import it.artmistech.pathfinder.sqlite.Database;

import javax.annotation.Nonnull;
import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NicknameUtils {
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
}
