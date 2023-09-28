package nl.han.oose.dea.spotitube.api.resources;

import jakarta.ws.rs.core.Response;
import nl.han.oose.dea.spotitube.business.dto.PlaylistsDTO;
import nl.han.oose.dea.spotitube.business.dto.PlaylistDTO;
import nl.han.oose.dea.spotitube.business.dto.TrackDTO;
import nl.han.oose.dea.spotitube.business.dto.TracksDTO;
import nl.han.oose.dea.spotitube.business.exceptions.CouldNotAddPlaylistException;
import nl.han.oose.dea.spotitube.business.exceptions.MissingParametersException;
import nl.han.oose.dea.spotitube.business.exceptions.PlaylistNotFoundException;
import nl.han.oose.dea.spotitube.business.exceptions.UserNotFoundException;
import nl.han.oose.dea.spotitube.interfaces.services.PlaylistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.net.HttpURLConnection.*;

public class PlaylistResourceTest {
  private  final String TOKEN = "6c3ca424-2692-4a46-8448-a617bddbddec";
  private  final String USERNAME = "user";
  private  final String PASSWORD = "pass";
  private final int PLAYLIST_ID = 1;

  private PlaylistResource sut;
  List<PlaylistDTO> playlists;
  private int totalLength;
  PlaylistsDTO allPlaylistsResponsedto;

  private PlaylistService mockedPlaylistService;
  private PlaylistDTO playlistDTO = new PlaylistDTO(1, "Heavy Metal", true, new ArrayList<>());

  @BeforeEach
  public void setup() {
    this.sut = new PlaylistResource();
    this.playlists = new ArrayList<>();
    playlists.add(new PlaylistDTO(1, "Heavy Metal", true, new ArrayList<>()));
    playlists.add(new PlaylistDTO(2, "Pop", false, new ArrayList<>()));
    allPlaylistsResponsedto = new PlaylistsDTO(playlists, totalLength);
    totalLength = 12345;
    this.mockedPlaylistService = mock(PlaylistService.class);
    this.sut.setPlaylistService(mockedPlaylistService);
  }

  // -- getAllPlaylists --
  @Test
  public void getAllPlaylists_Returns200_WhenPlaylistsFromUserFound() {
    // Arrange
    when(mockedPlaylistService.getPlaylists(TOKEN)).thenReturn(allPlaylistsResponsedto);

    // Act
    Response response = sut.getAllPlaylists(TOKEN);
    PlaylistsDTO actualAllPlaylistsResponse = (PlaylistsDTO) response.getEntity();

    // Assert
    assertEquals(HTTP_OK, response.getStatus());
    assertEquals(allPlaylistsResponsedto.getPlaylists(), actualAllPlaylistsResponse.getPlaylists());
  }

  @Test
  public void getAllPlaylists_Returns404_WhenNoUserFoundByToken() {
    //Arrange
    String errorMsg = "No user found with the given token.";

    when(mockedPlaylistService.getPlaylists(TOKEN)).thenThrow(new UserNotFoundException(errorMsg));

    Exception exception = assertThrows(UserNotFoundException.class, () -> {
      this.sut.getAllPlaylists(TOKEN);
    });

    //Assert
    assertEquals(UserNotFoundException.class, exception.getClass());
    assertEquals(HTTP_NOT_FOUND, PlaylistNotFoundException.STATUSCODE);
    assertEquals(errorMsg, exception.getMessage());
  }

  @Test
  public void getAllPlaylists_CallsGetPlaylists_FromPlaylistService()  {
    // Act
    this.sut.getAllPlaylists(TOKEN);

    //Assert
    verify(mockedPlaylistService).getPlaylists(TOKEN);
  }

  @Test
  public void getAllPlaylists_ReturnsAllPlaylistsResponseDTO_WhenTokenIsValid()   {
    //Arrange
    when(mockedPlaylistService.getPlaylists(TOKEN)).thenReturn(allPlaylistsResponsedto);

    //Act
    Response response = this.sut.getAllPlaylists(TOKEN);

    //Assert
    assertEquals(allPlaylistsResponsedto, response.getEntity());
  }

  // -- editPlaylistName --
  @Test
  public void editPlaylistName_CallsUpdatePlaylistName_FromPlaylistService()  {
    // Act
    this.sut.editPlaylistName(TOKEN, PLAYLIST_ID, playlistDTO);

    // Assert
    verify(mockedPlaylistService).updatePlaylistName(PLAYLIST_ID, TOKEN, playlistDTO);
  }

  @Test
  public void editPlaylistName_Returns201_WhenUpdateIsSuccessful()   {
    // Arrange
    when(mockedPlaylistService.updatePlaylistName(PLAYLIST_ID, TOKEN, playlistDTO)).thenReturn(allPlaylistsResponsedto);

    // Act
    Response response = this.sut.editPlaylistName(TOKEN, PLAYLIST_ID, playlistDTO);

    // Assert
    assertEquals(HTTP_CREATED, response.getStatus());
  }

  @Test
  public void editPlaylistName_ReturnsAllPlaylistsResponseDTO_AsEntity_WhenUpdateIsSuccessful()  {
    // Arrange
    when(mockedPlaylistService.updatePlaylistName(PLAYLIST_ID, TOKEN, playlistDTO)).thenReturn(allPlaylistsResponsedto);

    // Act
    Response response = this.sut.editPlaylistName(TOKEN, PLAYLIST_ID, playlistDTO);

    // Assert
    assertEquals(allPlaylistsResponsedto, response.getEntity());
  }

  @Test
  public void editPlaylistName_Returns400_WhenPlaylistDTOFromRequest_IsNotComplete()  {
    // Arrange
    PlaylistDTO incompletePlaylistDTO = new PlaylistDTO();
    String errorMsg = "Niet alle velden van het request object zijn aanwezig.";
    when(mockedPlaylistService.updatePlaylistName(PLAYLIST_ID, TOKEN, incompletePlaylistDTO)).thenThrow(new MissingParametersException(errorMsg));

    // Act
    Exception exception = assertThrows(MissingParametersException.class, () -> {
      this.sut.editPlaylistName(TOKEN, PLAYLIST_ID, incompletePlaylistDTO);
    });

    // Assert
    assertEquals(MissingParametersException.class, exception.getClass());
    assertEquals(errorMsg, exception.getMessage());
  }

  // -- addPlaylist --
  @Test
  public void addPlaylist_CallsAddPlaylist_FromPlaylistService()  {
    // Arrange
    PlaylistDTO newPlaylist = new PlaylistDTO(-1, "TestPlaylist", true, new ArrayList<>());

    // Act
    this.sut.addPlaylist(TOKEN, newPlaylist);

    // Assert
    verify(mockedPlaylistService).addPlaylist(newPlaylist, TOKEN);
  }

  @Test
  public void addPlaylist_Returns201_WhenPlaylistIsCreated()   {
    // Arrange
    PlaylistDTO newPlaylist = new PlaylistDTO(1, "TestPlaylist", true, new ArrayList<>());

    // Act
    Response response = this.sut.addPlaylist(TOKEN, newPlaylist);

    // Assert
    assertEquals(HTTP_CREATED, response.getStatus());
  }

  @Test
  public void addPlaylist_ReturnsNewPlaylistsResponseDTO_WithNewlyCreatedPlaylist_WhenPlaylistIsAdded()  {
    // Arrange
    PlaylistDTO newPlaylist = new PlaylistDTO(1, "TestPlaylist", true, new ArrayList<>());
    PlaylistsDTO expectedPlaylists = new PlaylistsDTO(Arrays.asList(new PlaylistDTO(), new PlaylistDTO(), newPlaylist), totalLength);
    when(mockedPlaylistService.addPlaylist(newPlaylist, TOKEN)).thenReturn(expectedPlaylists);

    // Act
    Response response = this.sut.addPlaylist(TOKEN, newPlaylist);
    PlaylistsDTO actualResponse = (PlaylistsDTO) response.getEntity();

    // Assert
    assertEquals(expectedPlaylists, actualResponse);
    assertTrue(actualResponse.getPlaylists().contains(newPlaylist));
  }

  @Test
  public void addPlaylist_Returns400_WhenPlaylistDTOFromRequest_IsNotComplete()  {
    // Arrange
    PlaylistDTO incompletePlaylistDTO = new PlaylistDTO();
    String errorMsg = "Could not ad playlist";
    when(mockedPlaylistService.addPlaylist(incompletePlaylistDTO, TOKEN)).thenThrow(new CouldNotAddPlaylistException(errorMsg));

    // Act
    Exception exception = assertThrows(CouldNotAddPlaylistException.class, () -> {
      this.sut.addPlaylist(TOKEN, incompletePlaylistDTO);
    });

    // Assert
    assertEquals(CouldNotAddPlaylistException.class, exception.getClass());
    assertEquals(errorMsg, exception.getMessage());
  }

  // -- deletePlaylist --
  @Test
  public void deletePlaylist_CallsDeletePlaylist_FromPlaylistService() {
    // Act
    this.sut.deletePlaylist(TOKEN, PLAYLIST_ID);

    // Assert
    verify(mockedPlaylistService).deletePlaylist(PLAYLIST_ID, TOKEN);
  }

  @Test
  public void deletePlaylist_Returns200_WhenPlaylistIsDeleted() {
    // Act
    Response response = this.sut.deletePlaylist(TOKEN, PLAYLIST_ID);

    // Assert
    assertEquals(HTTP_OK, response.getStatus());

  }

  @Test
  public void deletePlaylist_ReturnsUpdatedPlaylistsResponseDTO_WithoutRemovedPlaylist()  {
    // Arrange
    PlaylistDTO newPlaylist = new PlaylistDTO(PLAYLIST_ID, "TestPlaylist", true, new ArrayList<>());
    PlaylistDTO newPlaylist2 = new PlaylistDTO(5, "TestPlaylist2", true, new ArrayList<>());
    PlaylistsDTO expectedPlaylists = new PlaylistsDTO(Arrays.asList(newPlaylist2, newPlaylist), totalLength);
    when(mockedPlaylistService.addPlaylist(newPlaylist, TOKEN)).thenReturn(expectedPlaylists);

    PlaylistsDTO expectedNewPlaylists = new PlaylistsDTO(List.of(newPlaylist2), totalLength);
    when(mockedPlaylistService.deletePlaylist(PLAYLIST_ID, TOKEN)).thenReturn(expectedNewPlaylists);

    // Act
    Response response = this.sut.deletePlaylist(TOKEN, PLAYLIST_ID);
    PlaylistsDTO updatedPlaylists = (PlaylistsDTO) response.getEntity();

    // Assert
    assertEquals(expectedNewPlaylists, response.getEntity());
    assertFalse(updatedPlaylists.getPlaylists().contains(newPlaylist));
  }

  // -- getAllTracksFromPlaylist --
  @Test
  public void getAllTracksFromPlaylist_CallsGetTracks_FromPlaylistService() {
    // Act
    this.sut.getAllTracksFromPlaylist(TOKEN, PLAYLIST_ID);

    // Assert
    verify(mockedPlaylistService).getTracks(PLAYLIST_ID, TOKEN);
  }


  @Test
  public void getAllTracksFromPlaylist_ReturnsStatus200_OnSuccessfulGetAttempt()  {
    // Act
    Response response = this.sut.getAllTracksFromPlaylist(TOKEN, PLAYLIST_ID);

    // Assert
    assertEquals(HTTP_OK, response.getStatus());
  }
  @Test
  public void getAllTracksFromPlaylist_ReturnsTrackResponseDTO_AsEntity()  {
    TrackDTO track = new TrackDTO(1, "Papa", "Lala", 503, "Album", 213, "10-04-2023", "Leuke poekoe g", true);
    List<TrackDTO> tracks = List.of(track);
    PlaylistDTO playlist = new PlaylistDTO(PLAYLIST_ID, "playlistje", true, tracks);

    PlaylistsDTO expectedPlaylists = new PlaylistsDTO(Arrays.asList(new PlaylistDTO(), new PlaylistDTO(), playlist), totalLength);
    when(mockedPlaylistService.addPlaylist(playlist, TOKEN)).thenReturn(expectedPlaylists);

    TracksDTO tracksDTO = new TracksDTO(tracks);
    when(mockedPlaylistService.getTracks( PLAYLIST_ID, TOKEN)).thenReturn(tracksDTO);

    // Act
    Response response = this.sut.getAllTracksFromPlaylist(TOKEN, PLAYLIST_ID);
    TracksDTO actualTracksResponse = (TracksDTO) response.getEntity();

    // Assert
    assertEquals(tracksDTO, response.getEntity());
    assertEquals(tracksDTO.getTracks(), actualTracksResponse.getTracks());
    assertTrue(tracksDTO.getTracks().contains(track));
  }


  // -- addTrackToPlaylist --
  @Test
  public void addTrackToPlaylist_CallsAddTrack_FromPlaylistService()  {
    // Arrange
    TrackDTO track = new TrackDTO("testTrack", "performerName", 372, "albumName", 3, "12-03-1972", "descriptionText", true);

    // Act
    this.sut.addTrackToPlaylist(TOKEN, track, PLAYLIST_ID);

    // Assert
    verify(mockedPlaylistService).addTrack(track, TOKEN, PLAYLIST_ID);
  }

  @Test
  public void addTrackToPlaylist_Returns200_WhenTrackIsAdded()   {
    // Arrange
    TrackDTO track = new TrackDTO("testTrack", "performerName", 372, "albumName", 3, "12-03-1972", "descriptionText", true);

    // Act
    Response response = this.sut.addTrackToPlaylist(TOKEN, track, PLAYLIST_ID);

    // Assert
    assertEquals(HTTP_OK,response.getStatus());
  }

  @Test
  public void addTrackToPlaylist_ReturnsTracksDTO_WithAddedTrack(){
// Arrange
    TrackDTO track = new TrackDTO("testTrack", "performerName", 372, "albumName", 3, "12-03-1972", "descriptionText", true);
    TracksDTO expectedTracks = new TracksDTO(List.of(track));
    when(mockedPlaylistService.addTrack(track, TOKEN, PLAYLIST_ID)).thenReturn(expectedTracks);

    // Act
    Response response = this.sut.addTrackToPlaylist(TOKEN, track, PLAYLIST_ID);
    TracksDTO responseEntity = (TracksDTO) response.getEntity();
    // Assert
    assertEquals(expectedTracks, response.getEntity());
    assertTrue(responseEntity.getTracks().contains(track));
  }

  // -- deleteTrackFromPlaylist --
  @Test
  public void deleteTrackFromPlaylist_CallsDeleteTrack_FromPlaylistService() {
    // Arrange
    int trackID = 1;

    // Act
    this.sut.deleteTrackFromPlaylist(TOKEN, PLAYLIST_ID, trackID);

    // Assert
    verify(mockedPlaylistService).deleteTrack(TOKEN, PLAYLIST_ID, trackID);
  }
  @Test
  public void deleteTrackFromPlaylist_Returns200_WhenTrackIsDeleted() {
    // Arrange
    int trackID = 1;

    // Act
    Response response = this.sut.deleteTrackFromPlaylist(TOKEN, PLAYLIST_ID, trackID);

    // Assert
    assertEquals(HTTP_OK, response.getStatus());
  }

  @Test
  public void deleteTrackFromPlaylist_ReturnsTracksDTO_WithoutDeletedTrack() {
    // Arrange
    int trackID = 1;
    TrackDTO track = new TrackDTO("testTrack", "performerName", 372, "albumName", 3, "12-03-1972", "descriptionText", true);
    TracksDTO expectedTracks = new TracksDTO(new ArrayList<>());
    when(mockedPlaylistService.deleteTrack(TOKEN, PLAYLIST_ID, trackID)).thenReturn(expectedTracks);

    // Act
    Response response = this.sut.deleteTrackFromPlaylist(TOKEN, PLAYLIST_ID, trackID);
    TracksDTO responseEntity = (TracksDTO) response.getEntity();

    // Assert
    assertEquals(expectedTracks, responseEntity);
    assertFalse(responseEntity.getTracks().contains(track));
  }

}
