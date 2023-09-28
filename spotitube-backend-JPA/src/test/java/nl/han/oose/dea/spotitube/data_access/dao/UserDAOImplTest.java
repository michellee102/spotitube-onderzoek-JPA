package nl.han.oose.dea.spotitube.data_access.dao;

import nl.han.oose.dea.spotitube.business.exceptions.UnauthorizedException;
import nl.han.oose.dea.spotitube.business.exceptions.UserNotFoundException;
import nl.han.oose.dea.spotitube.data_access.models.User;
import nl.han.oose.dea.spotitube.data_access.util.DatabaseManager;
import nl.han.oose.dea.spotitube.interfaces.dao.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
public class UserDAOImplTest {
  private UserDAO sut;
  private DatabaseManager mockedDBManager;
  private Connection mockedConnection;
  private PreparedStatement mockedStatement;
  private ResultSet mockedResultset;

  private final String USERNAME = "username";
  private final String PASSWORD = "password";
  private final String TOKEN = "token";

  @BeforeEach
  public void setup() throws SQLException {
    this.mockedConnection = mock(Connection.class);
    this.mockedStatement = mock(PreparedStatement.class);
    this.mockedResultset = mock(ResultSet.class);
    this.mockedDBManager = mock(DatabaseManager.class);

    this.sut = new UserDAOImpl(mockedConnection);

    when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedStatement);
    when(mockedConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockedStatement);
  }

  @Test
  public void findByUsername_ReturnsUser_WhenUserExists() throws SQLException {
    // Arrange
    User expectedUser = new User(USERNAME, PASSWORD, TOKEN);

    when(mockedResultset.next()).thenReturn(true);
    when(mockedResultset.getString("username")).thenReturn(USERNAME);
    when(mockedResultset.getString("password")).thenReturn(PASSWORD);
    when(mockedResultset.getString("token")).thenReturn(TOKEN);
    when(mockedStatement.executeQuery()).thenReturn(mockedResultset);

    // Act
    User actualUser = this.sut.findByUsername(USERNAME);

    // Assert
    assertEquals(expectedUser.getUsername(), actualUser.getUsername());
    assertEquals(expectedUser.getPassword(), actualUser.getPassword());
    assertEquals(expectedUser.getToken(), actualUser.getToken());
  }

  @Test
  public void findByUsername_ExecutesCorrectly() throws SQLException {
    when(mockedResultset.next()).thenReturn(true);
    when(mockedResultset.getString("username")).thenReturn(USERNAME);
    when(mockedResultset.getString("password")).thenReturn(PASSWORD);
    when(mockedResultset.getString("token")).thenReturn(TOKEN);
    when(mockedStatement.executeQuery()).thenReturn(mockedResultset);

    this.sut.findByUsername(USERNAME);

    verify(mockedStatement).setString(1, USERNAME);
  }

  @Test
  public void findByUsername_ThrowsUserNotFoundException_WhenUserDoesntExists() throws SQLException {
    // Arrange
    String expectedMessage = "Could not find user: " + USERNAME;
    when(mockedStatement.executeQuery()).thenReturn(mockedResultset);
    when(mockedResultset.next()).thenReturn(false);

    // Act
    Exception exception = assertThrows(UserNotFoundException.class, () -> this.sut.findByUsername(USERNAME));

    // Assert
    assertEquals(expectedMessage, exception.getMessage());
  }

  @Test
  public void findByUsername_shouldThrowRuntimeException() throws SQLException {
    // Arrange
    when(mockedStatement.executeQuery()).thenThrow(SQLException.class);

    // Act & Assert
    assertThrows(RuntimeException.class, () -> sut.findByUsername(USERNAME));
  }

  @Test
  public void insertUser_ExecutesCorrectly() throws SQLException {
    // Arrange
    User user = new User(USERNAME, PASSWORD, TOKEN);
    when(mockedStatement.executeUpdate()).thenReturn(1);

    // Act
    this.sut.insertUser(user);

    // Assert
    verify(mockedStatement).setString(1, user.getUsername());
    verify(mockedStatement).setString(2, user.getPassword());
    verify(mockedStatement).setString(3, user.getToken());

  }

  @Test
  public void insertUser_ThrowsRuntimeException() throws SQLException {
    // Arrange
    User user = new User(USERNAME, PASSWORD, TOKEN);
    when(mockedStatement.executeUpdate()).thenThrow(SQLException.class);
    // Act
    assertThrows(RuntimeException.class, () -> this.sut.insertUser(user));
  }


  @Test
  public void findByToken_ReturnsUser_WhenUserExists() throws SQLException {
    // Arrange
    User expectedUser = new User(USERNAME, PASSWORD, TOKEN);

    when(mockedResultset.next()).thenReturn(true);
    when(mockedResultset.getString("username")).thenReturn(USERNAME);
    when(mockedResultset.getString("password")).thenReturn(PASSWORD);
    when(mockedResultset.getString("token")).thenReturn(TOKEN);
    when(mockedStatement.executeQuery()).thenReturn(mockedResultset);

    // Act
    User actualUser = this.sut.findByToken(TOKEN);

    // Assert
    assertEquals(expectedUser.getUsername(), actualUser.getUsername());
    assertEquals(expectedUser.getPassword(), actualUser.getPassword());
    assertEquals(expectedUser.getToken(), actualUser.getToken());
  }

  @Test
  public void findByToken_ThrowsUnauthorizedException_WhenUserDoesntExists() throws SQLException {
    // Arrange
    String expectedMessage = "No user found with the provided token.";
    when(mockedStatement.executeQuery()).thenReturn(mockedResultset);
    when(mockedResultset.next()).thenReturn(false);

    // Act
    Exception exception = assertThrows(UnauthorizedException.class, () -> this.sut.findByToken(TOKEN));

    // Assert
    assertEquals(expectedMessage, exception.getMessage());
  }

  @Test
  public void findByToken_shouldThrowRuntimeException() throws SQLException {
    // Arrange
    when(mockedStatement.executeQuery()).thenThrow(SQLException.class);

    // Act & Assert
    assertThrows(RuntimeException.class, () -> sut.findByToken(TOKEN));
  }

  @Test
  public void testConstructor_WithDatabaseManager_AsParam() {
    // Arrange
    when(mockedDBManager.connect()).thenReturn(mockedConnection);

    // Act
    UserDAOImpl userDAO = new UserDAOImpl(mockedDBManager);

    // Assert
    assertNotNull(userDAO);
  }

}
