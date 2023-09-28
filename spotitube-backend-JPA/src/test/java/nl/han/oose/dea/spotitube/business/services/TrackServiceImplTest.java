package nl.han.oose.dea.spotitube.business.services;

import nl.han.oose.dea.spotitube.business.dto.TracksDTO;
import nl.han.oose.dea.spotitube.data_access.models.Track;
import nl.han.oose.dea.spotitube.interfaces.dao.TrackDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TrackServiceImplTest {
  private TrackServiceImpl sut;
  private TrackDAO mockedTrackDAO;
  private final String TOKEN = "2347-324-s3-fsd";
  private final int PLAYLIST_ID = 1;
  private Track testTrack = new Track(1, "testTrack", "testPerformer",928);
  private Track testTrack2 = new Track(2, "testTrack2", "testPerformer2",930);

  @BeforeEach
  public void setup(){
    this.sut = new TrackServiceImpl();
    this.mockedTrackDAO = mock(TrackDAO.class);
    this.sut.setTrackDAO(mockedTrackDAO);
  }

  @Test
  public void getAvailableTracks_ReturnsTracksDTO_WithTracksFromGivenPlaylist(){
    // Arrange
    List<Track> tracks = Arrays.asList(testTrack, testTrack2);
    int trackAmount = 2;
    when(mockedTrackDAO.getAvailableTracks(PLAYLIST_ID, TOKEN)).thenReturn(tracks);

    // Act
    TracksDTO response = this.sut.getAvailableTracks(TOKEN, PLAYLIST_ID);

    // Assert
    assertEquals(response.getClass(), TracksDTO.class);
    assertEquals(response.getTracks().get(0).getTitle(),"testTrack" );
    assertEquals(response.getTracks().get(1).getTitle(), "testTrack2");
    assertEquals(response.getTracks().size(), trackAmount);
  }
}
