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
}
