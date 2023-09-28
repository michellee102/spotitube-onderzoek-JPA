package nl.han.oose.dea.spotitube.api.resources;

import jakarta.ws.rs.core.Response;
import nl.han.oose.dea.spotitube.business.dto.TrackDTO;
import nl.han.oose.dea.spotitube.business.dto.TracksDTO;
import nl.han.oose.dea.spotitube.interfaces.services.TrackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.net.HttpURLConnection.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class TrackResourceTest {
  private TrackResource sut;
  private final String TOKEN = "6c3ca424-2692-4a46-8448-a617bddbddec";
  private final int PLAYLIST_ID = 1;
  private TrackService mockedTrackService;

  @BeforeEach
  public void setup(){
    this.sut = new TrackResource();
    this.mockedTrackService = mock(TrackService.class);
    this.sut.setTrackService(mockedTrackService);
  }

  @Test
  public void getAvailableTracks_CallsGetAvailableTracks_FromTrackService()  {
    // Act
    this.sut.getAvailableTracks(TOKEN, PLAYLIST_ID);

    // Assert
    verify(mockedTrackService).getAvailableTracks(TOKEN, PLAYLIST_ID);
  }

  @Test
  public void getAvailableTracks_Returns200_WhenTracksAreReturned()   {
    // Act
    Response response = this.sut.getAvailableTracks(TOKEN, PLAYLIST_ID);

    // Assert
    assertEquals(HTTP_OK, response.getStatus());
  }

  @Test
  public void getAvailableTracks_ReturnsTracksDTO_WithAvailableTracks(){
    // Arrange
    TracksDTO expectedTracks = new TracksDTO(List.of(
      new TrackDTO("testTrack1", "performerName1", 372, "albumName1", 3, "12-03-1972", "descriptionText1", true),
      new TrackDTO("testTrack2", "performerName2", 431, "albumName2", 5, "01-02-1985", "descriptionText2", false)
    ));
    when(mockedTrackService.getAvailableTracks(TOKEN, PLAYLIST_ID)).thenReturn(expectedTracks);

    // Act
    Response response = this.sut.getAvailableTracks(TOKEN, PLAYLIST_ID);
    TracksDTO responseEntity = (TracksDTO) response.getEntity();

    // Assert
    assertEquals(expectedTracks, responseEntity);
    assertEquals(expectedTracks.getTracks().size(), responseEntity.getTracks().size());
    assertTrue(responseEntity.getTracks().containsAll(expectedTracks.getTracks()));
  }
}
