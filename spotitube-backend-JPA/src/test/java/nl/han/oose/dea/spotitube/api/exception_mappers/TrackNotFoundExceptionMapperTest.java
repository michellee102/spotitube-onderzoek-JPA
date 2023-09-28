package nl.han.oose.dea.spotitube.api.exception_mappers;

import jakarta.ws.rs.core.Response;
import nl.han.oose.dea.spotitube.business.exceptions.TrackNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrackNotFoundExceptionMapperTest {
  private TrackNotFoundExceptionMapper sut;
  private TrackNotFoundException testException;
  private final String EXCEPTION_MESSAGE = "Test exception message";

  @BeforeEach
  public void setup() {
    this.sut = new TrackNotFoundExceptionMapper();
    testException = new TrackNotFoundException(EXCEPTION_MESSAGE);
  }

  @Test
  public void toResponseReturnsExpectedResponse() {
    Response response = this.sut.toResponse(testException);

    assertEquals(HTTP_NOT_FOUND, response.getStatus());
    assertEquals(EXCEPTION_MESSAGE, response.getEntity());
  }
}
