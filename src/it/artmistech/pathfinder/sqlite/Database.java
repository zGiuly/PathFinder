package it.artmistech.pathfinder.sqlite;

import it.artmistech.pathfinder.utils.DatabaseUtils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    private final File location;
    private final String name;
    private File finalLocation;
    private Connection connection;
    public static final String DATABASE_URL = "jdbc:sqlite:";

    public Database(File location, String name) throws Throwable {
        this.location = location;
        this.name = name;
        createDatabase();
        startConnection();
    }

    /**
     * Create default database file
     * @throws Throwable
     */
    private void createDatabase() throws Throwable {
        finalLocation = new File(location, name);
        if(!finalLocation.exists()) {
            finalLocation.createNewFile();
        }
    }

    private void startConnection() throws Throwable {
        connection = DriverManager.getConnection(DATABASE_URL+finalLocation.getAbsolutePath());
    }

    /**
     * Get sql connection
     * @return
     */
    public Connection getConnection() {
        if(connection == null) {
            System.out.println("Database error from pathfinder");
        }
        return connection;
    }
}
