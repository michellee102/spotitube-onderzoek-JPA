package nl.han.oose.dea.spotitube.data_access.dao;

import nl.han.oose.dea.spotitube.business.dto.PlaylistDTO;
import nl.han.oose.dea.spotitube.business.dto.TrackDTO;
import nl.han.oose.dea.spotitube.business.exceptions.PlaylistNotFoundException;
import nl.han.oose.dea.spotitube.data_access.models.Playlist;
import nl.han.oose.dea.spotitube.data_access.models.Playlists;
import nl.han.oose.dea.spotitube.data_access.models.Track;
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

public class PlaylistDAOImplTest {
  private PlaylistDAOImpl sut;
  private TrackDAO mockedTrackDAO;
  private Connection mockedConnection;
  private PreparedStatement mockedStatement;
  private ResultSet mockedResultset;

  private final String USERNAME = "testusername";
  private final int PLAYLIST_ID = 1;
  private final String NEW_NAME = "New playlist name";
  private final String OWNER_NAME = "John Doe";

  @BeforeEach
  public void setup() throws SQLException {
    this.mockedConnection = mock(Connection.class);
    this.sut = new PlaylistDAOImpl(mockedConnection);
    this.mockedTrackDAO = mock(TrackDAO.class);
    this.sut.setTrackDAO(mockedTrackDAO);
    this.mockedStatement = mock(PreparedStatement.class);
    this.mockedResultset = mock(ResultSet.class);

    when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedStatement);
    when(mockedConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockedStatement);
  }


  @Test
  public void setTrackDAO_ShouldSetCorrectly() {
    sut.setTrackDAO(mockedTrackDAO);

    assertEquals(mockedTrackDAO, sut.getTrackDAO());
  }

  @Test
  public void findPlaylistsByUsername_ReturnsPlaylists() throws SQLException {
    List<Playlist> expectedPlaylists = new ArrayList<>();
    Playlist playlist1 = new Playlist(1, "Playlist 1", true, USERNAME, new ArrayList<>());
    Playlist playlist2 = new Playlist(2, "Playlist 2", true, USERNAME, new ArrayList<>());
    expectedPlaylists.add(playlist1);
    expectedPlaylists.add(playlist2);

    when(mockedResultset.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    when(mockedResultset.getString("name")).thenReturn(playlist1.getName()).thenReturn(playlist2.getName());
    when(mockedResultset.getInt("id")).thenReturn(playlist1.getId()).thenReturn(playlist2.getId());
    when(mockedResultset.getString("owner_name")).thenReturn(USERNAME);

    when(mockedStatement.executeQuery()).thenReturn(mockedResultset);

    // Act
    Playlists actualPlaylists = this.sut.findPlaylistsByUsername(USERNAME);

    assertEquals(Playlists.class, actualPlaylists.getClass());
    assertEquals(expectedPlaylists.get(0).getName(), actualPlaylists.getPlaylists().get(0).getName());
    assertEquals(expectedPlaylists.get(1).getName(), actualPlaylists.getPlaylists().get(1).getName());
  }

  @Test
  public void findPlaylistById_ThrowsPlaylistNotFoundException_WhenPlaylistNotFound() {
    // Arrange
    String errorMsg = "Could not find playlist with id " + PLAYLIST_ID;
    // Act
    Exception exception = assertThrows(PlaylistNotFoundException.class, () -> {
      this.sut.findPlaylistById(PLAYLIST_ID);
    });

    // Assert
    assertEquals(errorMsg, exception.getMessage());
  }

  @Test
  public void findPlaylistById_ExecutesCorrectly() throws SQLException {
    // Arrange
    when(mockedStatement.executeQuery()).thenReturn(mockedResultset);
    when(mockedResultset.next()).thenReturn(true);

    // Act
    sut.findPlaylistById(PLAYLIST_ID);

    // Assert
    verify(mockedStatement).setInt(1, PLAYLIST_ID);
    verify(mockedStatement).executeQuery();
  }

  @Test
  public void updateName_ExecutesCorrectly() throws SQLException {
    // Arrange
    when(mockedResultset.getString("name")).thenReturn(NEW_NAME);
    when(mockedResultset.getInt("id")).thenReturn(PLAYLIST_ID);
    when(mockedStatement.executeUpdate()).thenReturn(1);
    // Act
    sut.updateName(PLAYLIST_ID, NEW_NAME, OWNER_NAME);

    // Assert
    verify(mockedStatement).setString(1, NEW_NAME);
    verify(mockedStatement).setInt(3, PLAYLIST_ID);
    verify(mockedStatement).executeUpdate();
  }

  @Test
  public void updateName_ThrowsPlaylistNotFoundException_WhenNoPlaylistFound() throws SQLException {
    // Arrange
    String errorMsg = "No playlist found with the given id.";
    when(mockedStatement.executeUpdate()).thenReturn(0);

    // Act
    Exception exception = assertThrows(PlaylistNotFoundException.class, () -> sut.updateName(PLAYLIST_ID, NEW_NAME, OWNER_NAME));

    // Assert
    assertEquals(exception.getClass(), PlaylistNotFoundException.class);
    assertEquals(errorMsg, exception.getMessage());
  }

  @Test
  public void addPlaylist_ExecutesCorrectly() throws SQLException {
    // Arrange
    PlaylistDTO playlistDTO = new PlaylistDTO(1, "playlistName", false, new ArrayList<>());
    when(mockedStatement.executeUpdate()).thenReturn(1);
    when(mockedResultset.getString("name")).thenReturn(playlistDTO.getName());
    when(mockedResultset.getBoolean("owner")).thenReturn(playlistDTO.isOwner());
    when(mockedResultset.getString("username")).thenReturn(USERNAME);
    // Act
    sut.addPlaylist(USERNAME, playlistDTO);

    // Assert
    verify(mockedStatement).setString(1, playlistDTO.getName());
    verify(mockedStatement).setBoolean(2, true);
    verify(mockedStatement).setString(3, USERNAME);
    verify(mockedStatement).executeUpdate();
  }

  @Test
  public void deletePlaylist_ExecutesCorrectly() throws SQLException {
    // Arrange
    when(mockedStatement.executeUpdate()).thenReturn(1);

    // Act
    sut.deletePlaylist(PLAYLIST_ID, USERNAME);

    // Assert
    verify(mockedStatement).setInt(1, PLAYLIST_ID);
    verify(mockedStatement).setString(2, USERNAME);
    verify(mockedStatement).executeUpdate();
  }

  @Test
  public void deletePlaylist_ThrowsPlaylistNotFoundException_WhenNoPlaylistFound() throws SQLException {
    // Arrange
    String errorMsg = "Could not find playlist with id " + PLAYLIST_ID;
    when(mockedStatement.executeUpdate()).thenReturn(0);

    // Act
    Exception exception = assertThrows(PlaylistNotFoundException.class, () -> sut.deletePlaylist(PLAYLIST_ID, USERNAME));

    // Assert
    assertEquals(exception.getClass(), PlaylistNotFoundException.class);
    assertEquals(errorMsg, exception.getMessage());
  }

  @Test
  public void getTracksFromPlaylist_ReturnsListOfTracks_WhenSuccessful() throws SQLException {
    List<Track> expectedTracks = List.of(new Track(1, "testTrack", "testPerformer",928), new Track(2, "testTrack2", "testPerformer2",928));
    when(mockedStatement.executeQuery()).thenReturn(mockedResultset);
    when(mockedResultset.next()).thenReturn(true, true, false);
    when(mockedResultset.getInt("id")).thenReturn(1, 2);
    when(mockedResultset.getString("title")).thenReturn("testTrack", "testTrack2");
    when(mockedResultset.getString("performer")).thenReturn("testPerformer", "testPerformer2");
    when(mockedResultset.getInt("duration")).thenReturn(928, 928);

    // Act
    List<Track> actualTracks = this.sut.getTracksFromPlaylist(PLAYLIST_ID, USERNAME);

    // Assert
    assertEquals(expectedTracks.get(0).getTitle(), actualTracks.get(0).getTitle());
    assertEquals(expectedTracks.get(1).getTitle(), actualTracks.get(1).getTitle());
    verify(mockedStatement).setString(1, USERNAME);
    verify(mockedStatement).setInt(2, PLAYLIST_ID);
  }


  @Test
  public void addTrackToPlaylist_Successful() throws SQLException {
    TrackDTO trackDTO = new TrackDTO(3, "testTrack", "performerName", 372, "albumName", 3, "12-03-1972", "descriptionText", true);
    when(mockedStatement.executeQuery()).thenReturn(mockedResultset);
    when(mockedResultset.next()).thenReturn(true, false);

    when(mockedResultset.getInt("id")).thenReturn(PLAYLIST_ID);
    when(mockedResultset.getBoolean("offlineAvailable")).thenReturn(trackDTO.isOfflineAvailable());

    // Act
    this.sut.addTrackToPlaylist(trackDTO, PLAYLIST_ID);

    // Assert
    verify(mockedStatement, times(2)).setInt(1, PLAYLIST_ID);
    verify(mockedStatement).setBoolean(2, trackDTO.isOfflineAvailable());
    verify(mockedStatement).setInt(3, trackDTO.getId());
  }
  @Test
  public void deleteTrackFromPlaylist_ExecutesSuccessful() throws SQLException {
    // Arrange
    TrackDTO trackDTO = new TrackDTO(3, "testTrack", "performerName", 372, "albumName", 3, "12-03-1972", "descriptionText", true);
    when(mockedStatement.executeUpdate()).thenReturn(1);
    when(mockedResultset.getInt("id")).thenReturn(trackDTO.getId());

    // Act
    sut.deleteTrackFromPlaylist(trackDTO.getId());

    // Assert
    verify(mockedStatement).setInt(1, trackDTO.getId());
    verify(mockedStatement).executeUpdate();
  }


}
