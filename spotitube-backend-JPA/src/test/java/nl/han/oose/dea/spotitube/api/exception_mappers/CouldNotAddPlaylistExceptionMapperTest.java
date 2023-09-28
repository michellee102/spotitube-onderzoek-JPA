package nl.han.oose.dea.spotitube.api.exception_mappers;

import jakarta.ws.rs.core.Response;
import nl.han.oose.dea.spotitube.business.exceptions.CouldNotAddPlaylistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CouldNotAddPlaylistExceptionMapperTest {
  private CouldNotAddPlaylistExceptionMapper sut;
  private CouldNotAddPlaylistException testException;
  private final String EXCEPTION_MESSAGE = "Test exception message";

  @BeforeEach
  public void setup() {
    this.sut = new CouldNotAddPlaylistExceptionMapper();
    testException = new CouldNotAddPlaylistException(EXCEPTION_MESSAGE);
  }

  @Test
  public void toResponseReturnsExpectedResponse() {
    Response response = this.sut.toResponse(testException);

    assertEquals(HTTP_BAD_REQUEST, response.getStatus());
    assertEquals(EXCEPTION_MESSAGE, response.getEntity());
  }
}
