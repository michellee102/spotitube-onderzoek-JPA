package nl.han.oose.dea.spotitube.crosscutting_concerns;

import nl.han.oose.dea.spotitube.business.exceptions.UnauthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationTest {
  private Authentication sut;
  private String password = "testPassword";

  @BeforeEach
  public void setup(){
    this.sut = new Authentication();
  }

  @Test
  public void hashPassword_ReturnsHashedPassword(){
      // Act
    String hashedPw = this.sut.hashPassword(password);

    // Assert
    assertNotEquals(password, hashedPw);
  }

  @Test
  public void checkPassword_ThrowsUnauthorized_WhenPasswordDoesntMatch(){
    // Arrange
    String notTheSamePassw = "password";
    String errorMsg = "The password you entered is incorrect.";

    // Act
    Exception exception = assertThrows(UnauthorizedException.class, () -> {
      this.sut.checkPassword(password, notTheSamePassw);
    });

    // Assert
    assertEquals(UnauthorizedException.class, exception.getClass());
    assertEquals(errorMsg, exception.getMessage());
  }

}
