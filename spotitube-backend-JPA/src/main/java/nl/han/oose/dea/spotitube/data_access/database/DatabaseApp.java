package nl.han.oose.dea.spotitube.data_access.database;
import nl.han.oose.dea.spotitube.data_access.util.DatabaseManager;

public class DatabaseApp {
  private static     DatabaseManager databaseManager;
  private static DatabaseSetup databaseSetup;

  public static void setDatabaseManager(DatabaseManager databaseManager) {
    DatabaseApp.databaseManager = databaseManager;
  }

  public static void setDatabaseSetup(DatabaseSetup databaseSetup) {
    DatabaseApp.databaseSetup = databaseSetup;
  }

  public static void main(String[] args) {
     databaseManager = new DatabaseManager();
     databaseSetup = new DatabaseSetup(databaseManager);
    databaseSetup.setupSpotitubeDatabase();
  }
}
