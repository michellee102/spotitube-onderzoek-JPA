package nl.han.oose.dea.spotitube.data_access.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DatabasePropertiesTest {
  private final String EXPECTED_DRIVER = "com.mysql.cj.jdbc.Driver";
  private final String EXPECTED_USERNAME = "root";
  private final String EXPECTED_PASSWORD = "Mijo-200126";
  private final String EXPECTED_CONNETIONSTRING = "jdbc:mysql://127.0.0.1:3306/spotitube";
  private DatabaseProperties sut;

    @Test
  public void databasePropertiesConstructor_LoadsPropertiesCorrect(){
      this.sut = new DatabaseProperties();

      assertEquals(EXPECTED_DRIVER, sut.getDriver());
      assertEquals(EXPECTED_CONNETIONSTRING, sut.getConnectionString());
      assertEquals(EXPECTED_PASSWORD, sut.getPassword());
      assertEquals(EXPECTED_USERNAME, sut.getUsername());

    }
}
