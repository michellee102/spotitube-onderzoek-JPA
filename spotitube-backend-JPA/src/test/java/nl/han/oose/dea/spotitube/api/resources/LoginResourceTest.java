package nl.han.oose.dea.spotitube.api.resources;

import jakarta.ws.rs.core.Response;
import nl.han.oose.dea.spotitube.business.dto.LoginRequestDTO;
import nl.han.oose.dea.spotitube.business.dto.LoginResponseDTO;
import nl.han.oose.dea.spotitube.business.exceptions.UnauthorizedException;
import nl.han.oose.dea.spotitube.interfaces.services.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static java.net.HttpURLConnection.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class LoginResourceTest {
  private LoginResource sut;
  private UserService mockedUserService;
  private static final String TOKEN = "6c3ca424-2692-4a46-8448-a617bddbddec";
  private static final String USERNAME = "testUser";
  private static final String PASSWORD = "testPass";
  private final LoginRequestDTO LOGIN_REQUEST = new LoginRequestDTO(USERNAME, PASSWORD);
  private final LoginResponseDTO LOGIN_RESPONSE = new LoginResponseDTO(TOKEN, USERNAME);


  @BeforeEach
  public void setup() {
    this.sut = new LoginResource();
    this.mockedUserService = mock(UserService.class);
    this.sut.setUserService(mockedUserService);
  }

  @Test
  public void authenticateUser_CallsLogin_FromUserService() throws UnauthorizedException {
    // Act
    this.sut.authenticateUser(LOGIN_REQUEST);

    //Assert
    verify(mockedUserService).login(USERNAME, PASSWORD);
  }

  @Test
  public void authenticateUser_ReturnsStatus200_WhenLoginIsSuccessful() throws UnauthorizedException {
    // Arrange
    when(mockedUserService.login(USERNAME, PASSWORD)).thenReturn(TOKEN);

    // Act
    Response response = sut.authenticateUser(LOGIN_REQUEST);
    LoginResponseDTO actualLoginResponse = (LoginResponseDTO) response.getEntity();

    // Assert
    assertEquals(HTTP_OK, response.getStatus());
    assertEquals(LOGIN_RESPONSE.getUser(), actualLoginResponse.getUser());
    assertEquals(LOGIN_RESPONSE.getToken(), actualLoginResponse.getToken());
  }

  @Test
  public void authenticateUser_ReturnsStatus401_WhenLoginIsUnsuccessful() throws UnauthorizedException {
    // Arrange
    String errorMsg = "The password you entered is incorrect.";
    when(mockedUserService.login(USERNAME, PASSWORD)).thenThrow(new UnauthorizedException("The password you entered is incorrect."));

    Exception exception = assertThrows(UnauthorizedException.class, () -> {
      sut.authenticateUser(LOGIN_REQUEST);
    });

    // Assert
    assertEquals(UnauthorizedException.class, exception.getClass());
    assertEquals(HTTP_UNAUTHORIZED, UnauthorizedException.STATUSCODE);
    assertEquals(errorMsg, exception.getMessage());
  }

  @Test
  public void authenticateUser_ReturnsLoginResponseDTOAsEntity_WhenLoginIsSuccessful() throws UnauthorizedException {
    // Arrange
    when(mockedUserService.login(USERNAME, PASSWORD)).thenReturn(LOGIN_RESPONSE.getToken());

    // Act
    Response response = this.sut.authenticateUser(LOGIN_REQUEST);
    LoginResponseDTO actual = (LoginResponseDTO) response.getEntity();

    // Assert
    assertEquals(LOGIN_RESPONSE.getClass(), actual.getClass());
    assertEquals(LOGIN_RESPONSE.getUser(), actual.getUser());
    assertEquals(LOGIN_RESPONSE.getToken(), actual.getToken());
    assertEquals(HTTP_OK, response.getStatus());
  }




}
