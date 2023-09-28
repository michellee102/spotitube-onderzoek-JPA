package nl.han.oose.dea.spotitube.data_access.util;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseManager {
  private final DatabaseProperties DATABASE_PROPERTIES = new DatabaseProperties();
  private final Logger LOGGER = Logger.getLogger(getClass().getName());


  public Connection connect() {
    Connection connection = null;
    try {
      // Haal de driver op
      Class.forName(DATABASE_PROPERTIES.getDriver());
      // Maak connectie met DB
      connection = DriverManager.getConnection(DATABASE_PROPERTIES.getConnectionString(), DATABASE_PROPERTIES.getUsername(), DATABASE_PROPERTIES.getPassword());
    } catch (ClassNotFoundException | SQLException e) {
      LOGGER.log(Level.SEVERE, "Connecting to database " + DATABASE_PROPERTIES.getConnectionString() + " went wrong");
    }
    return connection;
  }


}
