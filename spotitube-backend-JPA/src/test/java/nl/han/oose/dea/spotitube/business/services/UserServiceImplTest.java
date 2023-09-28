package nl.han.oose.dea.spotitube.business.services;

import nl.han.oose.dea.spotitube.business.exceptions.UnauthorizedException;
import nl.han.oose.dea.spotitube.crosscutting_concerns.Authentication;
import nl.han.oose.dea.spotitube.data_access.models.User;
import nl.han.oose.dea.spotitube.interfaces.dao.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static java.net.HttpURLConnection.*;
import static nl.han.oose.dea.spotitube.crosscutting_concerns.Authentication.*;

public class UserServiceImplTest {
  private UserServiceImpl sut;
  private UserDAO mockedUserDAO;
  private Authentication mockedAuth;
  private final String USERNAME = "testusername";
  private final String PASSWORD = "testpassword";
  private final String TOKEN = "2347-324-s3-fsd";
  private User user = new User(USERNAME, PASSWORD, TOKEN);
  @BeforeEach
  public void setup(){
    this.sut = new UserServiceImpl();
    this.mockedUserDAO = mock(UserDAO.class);
    this.mockedAuth = mock(Authentication.class);
    this.sut.setAuthentication(mockedAuth);
    this.sut.setUserDAO(mockedUserDAO);
  }

  @Test
  public void login_returnsToken_WhenUserFound_AndPasswordMatches(){
    when(mockedUserDAO.findByUsername(USERNAME)).thenReturn(user);

    // Act
    String response = this.sut.login(USERNAME, PASSWORD);

    // Assert
    assertEquals(TOKEN, response);
  }

  @Test
  public void login_CreatesNewUser_WhenUserNotFound_AndReturnsToken(){
    when(mockedUserDAO.findByUsername(USERNAME)).thenReturn(null);

    // Act
    String response = this.sut.login(USERNAME, PASSWORD);

    // Assert
    assertTrue(!response.isEmpty());
  }

  @Test
  public void login_CallsFindByUsername_FromUserDAO(){
    this.sut.login(USERNAME, PASSWORD);

    // Assert
    verify(mockedUserDAO).findByUsername(USERNAME);
  }

  @Test
  public void verifyToken_ReturnsFalse_WhenUserDoesntExists(){
    //Arrange
    when(mockedUserDAO.findByToken(TOKEN)).thenReturn(null);

    // Act
    Boolean response = this.sut.verifyToken(TOKEN);

    // Assert
    assertFalse(response);
  }

  @Test
  public void verifyToken_ReturnsTrue_WhenUserExists(){
    //Arrange
    when(mockedUserDAO.findByToken(TOKEN)).thenReturn(user);

    // Act
    Boolean response = this.sut.verifyToken(TOKEN);

    // Assert
    assertTrue(response);
  }

  @Test
  public void verifyToken_CallsFindByToken_FromUserDAO(){
    this.sut.verifyToken(TOKEN);

    // Assert
    verify(mockedUserDAO).findByToken(TOKEN);
  }

  @Test
  public void getUserByToken_ReturnsUser_WhenUserExists(){
    //Arrange
    when(mockedUserDAO.findByToken(TOKEN)).thenReturn(user);

    // Act
    User response = this.sut.getUserByToken(TOKEN);

    // Assert
    assertEquals(response, user);
  }

  @Test
  public void getUserByToken_ThrowsUnauthorizedException_WhenNoUserFound(){
    //Arrange
    String errormsg = "No user found with the provided token.";
    when(mockedUserDAO.findByToken(TOKEN)).thenThrow(new UnauthorizedException(errormsg));

    // Act
    Exception exception = assertThrows(UnauthorizedException.class, () -> {
      this.sut.getUserByToken(TOKEN);
    });

    // Assert
    assertEquals(UnauthorizedException.class, exception.getClass());
    assertEquals(HTTP_UNAUTHORIZED, UnauthorizedException.STATUSCODE);
    assertEquals(errormsg, exception.getMessage());
  }


  @Test
  public void createNewUser_ReturnsUserObject_WithHashedPassword(){
    // Act
    User response = this.sut.createNewUser(USERNAME, PASSWORD);

    // Assert
    assertEquals(USERNAME, response.getUsername());
    assertNotEquals(PASSWORD, response.getPassword());
  }

  @Test
  public void createNewUser_CallsHashPassword_FromAuthentication(){
    // Act
    this.sut.createNewUser(USERNAME, PASSWORD);

    // Assert
    verify(mockedAuth).hashPassword(PASSWORD);
  }

  @Test
  public void verifyCredentials_ReturnsUsersToken(){
    // Act
    String response = this.sut.verifyCredentials(user, PASSWORD);

    // Assert
    assertEquals(TOKEN, response);
  }
}
