package it.artmistech.pathfinder.utils;

import it.artmistech.pathfinder.sqlite.Database;

import javax.annotation.Nonnull;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseUtils {
    /**
     * Create default table for nickname command
     * @param database
     */
    public static void createDefaultNicknameTable(@Nonnull Database database) {
        String tableCreation = "CREATE TABLE IF NOT EXISTS playerNickname (\n" +
                " realname TEXT NOT NULL,\n" +
                " newname TEXT NOT NULL\n" +
                ");";

        try(PreparedStatement statement = database.getConnection().prepareStatement(tableCreation)) {
            statement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create default table for economy
     * @param database
     */
    public static void createDefaultEconomyTable(@Nonnull Database database) {
        String tableCreation = "CREATE TABLE IF NOT EXISTS playerEconomy (\n" +
                " name TEXT NOT NULL,\n" +
                " balance DOUBLE NOT NULL\n" +
                ");";

        try(PreparedStatement statement = database.getConnection().prepareStatement(tableCreation)) {
            statement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create default table for ignored users
     * @param database
     */
    public static void createDefaultIgnoreTable(@Nonnull Database database) {
        String tableCreation = "CREATE TABLE IF NOT EXISTS playerIgnore (\n" +
                " name TEXT NOT NULL,\n" +
                " ignoredUsers ARRAY NOT NULL\n" +
                ");";

        try(PreparedStatement statement = database.getConnection().prepareStatement(tableCreation)) {
            statement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
