package nl.han.oose.dea.spotitube.data_access.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class DatabaseManagerTest {
  private DatabaseManager sut;


  @BeforeEach
  public void setup(){
    this.sut = new DatabaseManager();
  }

  @Test
  public void testConnectReturnsConnection() {
    Connection connection = sut.connect();
    assertNotNull(connection);
  }

  @Test
  public void testConnectCreatesWorkingConnection() throws SQLException {
    Connection connection = sut.connect();

      Statement stmt = connection.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT 1");
      assertTrue(rs.next());
      assertEquals(1, rs.getInt(1));

  }

}
