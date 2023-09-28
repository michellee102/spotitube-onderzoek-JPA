package nl.han.oose.dea.spotitube.api.exception_mappers;

import jakarta.ws.rs.core.Response;
import nl.han.oose.dea.spotitube.business.exceptions.CouldNotAddPlaylistException;
import nl.han.oose.dea.spotitube.business.exceptions.MissingParametersException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MissingParametersExceptionMapperTest {
  private MissingParametersExceptionMapper sut;
  private MissingParametersException testException;
  private final String EXCEPTION_MESSAGE = "Test exception message";

  @BeforeEach
  public void setup() {
    this.sut = new MissingParametersExceptionMapper();
    testException = new MissingParametersException(EXCEPTION_MESSAGE);
  }

  @Test
  public void toResponseReturnsExpectedResponse() {
    Response response = this.sut.toResponse(testException);

    assertEquals(HTTP_BAD_REQUEST, response.getStatus());
    assertEquals(EXCEPTION_MESSAGE, response.getEntity());
  }
}
