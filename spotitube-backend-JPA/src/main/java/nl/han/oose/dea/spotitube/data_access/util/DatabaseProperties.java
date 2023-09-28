package nl.han.oose.dea.spotitube.data_access.util;

import java.io.IOException;
import java.util.Properties;

public class DatabaseProperties {

  private Properties properties;

  public DatabaseProperties() {
    properties = new Properties();
    try {
      properties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public String getConnectionString () {
    return properties.getProperty("connectionString");
  }

  public String getDriver () {
    return properties.getProperty("driver");
  }

  public String getUsername () {
    return properties.getProperty("user");
  }

  public String getPassword () {
    return properties.getProperty("pass");
  }

}
