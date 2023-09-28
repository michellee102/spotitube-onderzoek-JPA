package nl.han.oose.dea.spotitube.api.exception_mappers;

import jakarta.ws.rs.core.Response;
import nl.han.oose.dea.spotitube.business.exceptions.UnauthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnauthorizedExceptionMapperTest {
  private UnauthorizedExceptionMapper sut;
  private UnauthorizedException testException;
  private final String EXCEPTION_MESSAGE = "Test exception message";

  @BeforeEach
  public void setup() {
    this.sut = new UnauthorizedExceptionMapper();
    testException = new UnauthorizedException(EXCEPTION_MESSAGE);
  }

  @Test
  public void toResponseReturnsExpectedResponse() {
    Response response = this.sut.toResponse(testException);

    assertEquals(HTTP_UNAUTHORIZED, response.getStatus());
    assertEquals(EXCEPTION_MESSAGE, response.getEntity());
  }
}
