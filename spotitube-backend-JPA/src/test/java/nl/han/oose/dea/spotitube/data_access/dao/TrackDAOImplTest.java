package nl.han.oose.dea.spotitube.data_access.dao;

import nl.han.oose.dea.spotitube.business.dto.PlaylistDTO;
import nl.han.oose.dea.spotitube.business.dto.TrackDTO;
import nl.han.oose.dea.spotitube.business.exceptions.PlaylistNotFoundException;
import nl.han.oose.dea.spotitube.business.exceptions.TrackNotFoundException;
import nl.han.oose.dea.spotitube.data_access.models.Playlist;
import nl.han.oose.dea.spotitube.data_access.models.Playlists;
import nl.han.oose.dea.spotitube.data_access.models.Track;
import nl.han.oose.dea.spotitube.data_access.models.User;
import nl.han.oose.dea.spotitube.data_access.util.DatabaseManager;
import nl.han.oose.dea.spotitube.interfaces.dao.TrackDAO;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class TrackDAOImplTest {
  private TrackDAOImpl sut;
  private DatabaseManager mockedDBManager;
  private Connection mockedConnection;
  private PreparedStatement mockedStatement;
  private ResultSet mockedResultset;

  private final String USERNAME = "testusername";
  private final int TRACK_ID = 1;
  private final int PLAYLIST_ID = 1;
  private final String NEW_NAME = "New playlist name";
  private final String OWNER_NAME = "John Doe";

  @BeforeEach
  public void setup() throws SQLException {
    this.mockedDBManager = mock(DatabaseManager.class);
    this.mockedConnection = mock(Connection.class);
    this.sut = new TrackDAOImpl(mockedConnection);

    this.mockedStatement = mock(PreparedStatement.class);
    this.mockedResultset = mock(ResultSet.class);

    when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedStatement);
    when(mockedConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockedStatement);
  }

  @Test
  public void getAvailableTracks_shouldReturnTracks() throws SQLException {
// Arrange
    List<Track> expectedTracks = List.of(new Track(1, "testTrack", "testPerformer", 928), new Track(2, "testTrack2", "testPerformer2", 930));

    when(mockedStatement.executeQuery()).thenReturn(mockedResultset);
    when(mockedResultset.next()).thenReturn(true, true, false);
    when(mockedResultset.getInt("id")).thenReturn(1, 2);
    when(mockedResultset.getString("title")).thenReturn("testTrack", "testTrack2");
    when(mockedResultset.getString("performer")).thenReturn("testPerformer", "testPerformer2");
    when(mockedResultset.getInt("duration")).thenReturn(928, 930);

// Act
    List<Track> actualTracks = sut.getAvailableTracks(1, "testUsername");

// Assert
    assertEquals(expectedTracks.size(), actualTracks.size());
    for (int i = 0; i < expectedTracks.size(); i++) {
      assertEquals(expectedTracks.get(i).getId(), actualTracks.get(i).getId());
      assertEquals(expectedTracks.get(i).getTitle(), actualTracks.get(i).getTitle());
      assertEquals(expectedTracks.get(i).getPerformer(), actualTracks.get(i).getPerformer());
      assertEquals(expectedTracks.get(i).getDuration(), actualTracks.get(i).getDuration());
      assertEquals(expectedTracks.get(i).getAlbum(), actualTracks.get(i).getAlbum());
      assertEquals(expectedTracks.get(i).getPlayCount(), actualTracks.get(i).getPlayCount());
    }
  }

  @Test
  public void getAvailableTracks_shouldThrowRuntimeException() throws SQLException {
    // Arrange
    when(mockedStatement.executeQuery()).thenThrow(SQLException.class);

    // Act & Assert
    assertThrows(RuntimeException.class, () -> sut.getAvailableTracks(1, "testUsername"));
  }



  @Test
  public void findTrackById_executesSuccessful() throws SQLException {
// Arrange
    when(mockedStatement.executeQuery()).thenReturn(mockedResultset);
    when(mockedResultset.next()).thenReturn(true, false);
    when(mockedResultset.getInt("id")).thenReturn(TRACK_ID);
// Act
   sut.findTrackById(TRACK_ID);

// Assert
    verify(mockedStatement).setInt(1, TRACK_ID);
  }

  @Test
  public void findTrackById_shouldThrowTrackNotFoundException() throws SQLException {
// Arrange
    String errorMsg = "Could not find track with id " + TRACK_ID;
    when(mockedStatement.executeQuery()).thenReturn(mockedResultset);
    when(mockedResultset.next()).thenReturn(false);

// Act & Assert
    Exception exception = assertThrows(TrackNotFoundException.class, () -> sut.findTrackById(TRACK_ID));

    assertEquals(errorMsg,exception.getMessage() );
  }



  @Test
  public void findTrackById_shouldThrowRuntimeException() throws SQLException {
    // Arrange
    when(mockedStatement.executeQuery()).thenThrow(SQLException.class);

    // Act & Assert
    assertThrows(RuntimeException.class, () -> sut.findTrackById(1));
  }

  @Test
  public void testConstructor() {
    assertEquals(mockedConnection, sut.getCONNECTION());
  }

  @Test
  public void testConstructor_WithDatabaseManager_AsParams() {
    // Arrange
    when(mockedDBManager.connect()).thenReturn(mockedConnection);

    // Act
    TrackDAO trackDAO = new TrackDAOImpl(mockedDBManager);

    // Assert
    assertNotNull(trackDAO);
  }

//  @Test
//  public void getAvailableTracks_ThrowsRuntimeException() throws SQLException {
//    // Arrange
//    when(mockedStatement.executeQuery()).thenThrow(new RuntimeException());
//    // Act
//    assertThrows(RuntimeException.class, () -> this.sut.getAvailableTracks(PLAYLIST_ID, USERNAME));
//  }
}
