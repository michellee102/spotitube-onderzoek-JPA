package nl.han.oose.dea.spotitube.business.services;

import nl.han.oose.dea.spotitube.business.dto.PlaylistDTO;
import nl.han.oose.dea.spotitube.business.dto.PlaylistsDTO;
import nl.han.oose.dea.spotitube.business.dto.TracksDTO;
import nl.han.oose.dea.spotitube.data_access.models.Playlist;
import nl.han.oose.dea.spotitube.data_access.models.Playlists;
import nl.han.oose.dea.spotitube.data_access.models.Track;
import nl.han.oose.dea.spotitube.data_access.models.User;
import nl.han.oose.dea.spotitube.interfaces.dao.PlaylistDAO;
import nl.han.oose.dea.spotitube.interfaces.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlaylistServiceImplTest {
  private PlaylistServiceImpl sut;
  private PlaylistDAO mockedPlaylistDAO;
  private UserService mockedUserService;
  private final String TOKEN = "2347-324-s3-fsd";
  private final User TEST_USER = new User("testUser", "testPass", TOKEN);
  private Track testTrack = new Track(1, "testTrack", "testPerformer",928);
  private Playlists testPlaylists = new Playlists(List.of(new Playlist(1, "playlist1", true, TEST_USER.getUsername(), List.of(testTrack))));
  String newPlaylistName = "nieuwe naam";
  PlaylistDTO playlistDTO = new PlaylistDTO(1, newPlaylistName, false, new ArrayList<>());
  @BeforeEach
  public void setup(){
    this.sut = new PlaylistServiceImpl();
    this.mockedPlaylistDAO = mock(PlaylistDAO.class);
    this.mockedUserService = mock(UserService.class);
    this.sut.setPlaylistDAO(mockedPlaylistDAO);
    this.sut.setUserService(mockedUserService);

  }

  @Test
  public void getPlaylists_ReturnsPlaylistsDTO(){
    // Arrange
    when(mockedUserService.getUserByToken(TOKEN)).thenReturn(TEST_USER);
    when(mockedPlaylistDAO.findPlaylistsByUsername(TEST_USER.getUsername())).thenReturn(testPlaylists);

    // Act
    PlaylistsDTO response = this.sut.getPlaylists(TOKEN);

    // Assert
    assertEquals(response.getClass(), PlaylistsDTO.class);
    assertEquals(response.getPlaylists().get(0).getId(), testPlaylists.getPlaylists().get(0).getId());
    assertEquals(response.getPlaylists().get(0).getName(), testPlaylists.getPlaylists().get(0).getName());
  }

  @Test
  public void updatePlaylistName_ReturnsPlaylistsDTO_WithUpdatedPlaylistName(){
    // Arrange
    when(mockedUserService.getUserByToken(TOKEN)).thenReturn(TEST_USER);

    Playlists updatedPlaylists = new Playlists(List.of(new Playlist(1, newPlaylistName, true, TEST_USER.getUsername(), List.of(new Track(1, "testTrack", "testPerformer",928)))));
    when(mockedPlaylistDAO.findPlaylistsByUsername(TEST_USER.getUsername())).thenReturn(updatedPlaylists);

    // Act
    PlaylistsDTO playlistsResponse = this.sut.updatePlaylistName(1, TOKEN, playlistDTO);

    // Assert
    assertEquals(playlistsResponse.getPlaylists().get(0).getName(), newPlaylistName);
  }

  @Test
  public void addPlaylist_ReturnsPlaylistsDTO_WithNewPlaylist(){
    when(mockedUserService.getUserByToken(TOKEN)).thenReturn(TEST_USER);

    Playlists updatedPlaylists = new Playlists(Arrays.asList(new Playlist(1, "playlist1", true, TEST_USER.getUsername(), List.of(new Track(1, "testTrack", "testPerformer",928))), new Playlist(2, newPlaylistName, true, TEST_USER.getUsername(), new ArrayList<>())));
    when(mockedPlaylistDAO.findPlaylistsByUsername(TEST_USER.getUsername())).thenReturn(updatedPlaylists);
    // Act
    PlaylistsDTO playlistsResponse = this.sut.addPlaylist(playlistDTO, TOKEN);

    // Assert
    assertEquals(playlistsResponse.getPlaylists().get(1).getName(), newPlaylistName );
    assertEquals(playlistsResponse.getPlaylists().get(1).getId(), 2 );
  }

  @Test
  public void deletePlaylist_ReturnsPlaylistsDTO_WithoutDeletedPlaylist(){
    when(mockedUserService.getUserByToken(TOKEN)).thenReturn(TEST_USER);

    Playlists updatedPlaylists = new Playlists(List.of(new Playlist(1, "playlist1", true, TEST_USER.getUsername(), List.of(new Track(1, "testTrack", "testPerformer", 928)))));
    when(mockedPlaylistDAO.findPlaylistsByUsername(TEST_USER.getUsername())).thenReturn(updatedPlaylists);
    // Act
    PlaylistsDTO playlistsResponse = this.sut.deletePlaylist(2, TOKEN);

    // Assert
    assertFalse(playlistsResponse.getPlaylists().stream().anyMatch(playlist -> playlist.getName().equals(newPlaylistName)));
    assertTrue(playlistsResponse.getPlaylists().stream().anyMatch(playlist -> playlist.getName().equals("playlist1")));
  }

  @Test
  public void addTrack_ReturnsTracksDTO(){
    when(mockedUserService.getUserByToken(TOKEN)).thenReturn(TEST_USER);
    List<Track> tracks = List.of(testTrack);
    when(mockedPlaylistDAO.getTracksFromPlaylist(1, TEST_USER.getUsername())).thenReturn(tracks);

    // Act
    TracksDTO response = this.sut.getTracks(1, TOKEN);

    // Assert
    assertEquals(response.getClass(), TracksDTO.class);
    assertEquals(response.getTracks().get(0).getTitle(), "testTrack");
  }
}
