package nl.han.oose.dea.spotitube.business.services;

import jakarta.inject.Inject;
import nl.han.oose.dea.spotitube.business.dto.PlaylistsDTO;
import nl.han.oose.dea.spotitube.business.dto.PlaylistDTO;
import nl.han.oose.dea.spotitube.business.dto.TrackDTO;
import nl.han.oose.dea.spotitube.business.dto.TracksDTO;
import nl.han.oose.dea.spotitube.data_access.models.Playlist;
import nl.han.oose.dea.spotitube.data_access.models.Playlists;
import nl.han.oose.dea.spotitube.data_access.models.Track;
import nl.han.oose.dea.spotitube.data_access.models.User;
import nl.han.oose.dea.spotitube.interfaces.dao.PlaylistDAO;
import nl.han.oose.dea.spotitube.data_access.data_mappers.PlaylistMapper;
import nl.han.oose.dea.spotitube.data_access.data_mappers.TrackMapper;
import nl.han.oose.dea.spotitube.interfaces.services.PlaylistService;
import nl.han.oose.dea.spotitube.interfaces.services.UserService;

import java.util.Collection;
import java.util.List;


public class PlaylistServiceImpl implements PlaylistService {
  private PlaylistDAO playlistDAO;
  private UserService userService;
  private final PlaylistMapper PLAYLIST_MAPPER = new PlaylistMapper();
  private final TrackMapper TRACK_MAPPER = new TrackMapper();

  @Inject
  public void setPlaylistDAO(PlaylistDAO playlistDAO) {
    this.playlistDAO = playlistDAO;
  }

  @Inject
  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  @Override
  public PlaylistsDTO getPlaylists(String token) {
    User user = userService.getUserByToken(token);
    Playlists playlists = playlistDAO.findPlaylistsByUsername(user.getUsername());
    System.out.println("GAAT GOED TOT HIERRRRRRRRRRRRRR");
    this.addTracksToPlaylists(playlists, user.getUsername());
    return new PlaylistsDTO(PLAYLIST_MAPPER.mapToAllPlaylistsResponseDTO(playlists), calculatePlaylistLength(playlists));
  }

  @Override
  public PlaylistsDTO updatePlaylistName(int playlistId, String token, PlaylistDTO playlistDTO) {
    User user = userService.getUserByToken(token);
    playlistDAO.updateName(playlistId, playlistDTO.getName(), user.getUsername());
    Playlists playlists = playlistDAO.findPlaylistsByUsername(user.getUsername());
    return new PlaylistsDTO(PLAYLIST_MAPPER.mapToAllPlaylistsResponseDTO(playlists), calculatePlaylistLength(playlists));
  }

  @Override
  public PlaylistsDTO addPlaylist(PlaylistDTO playlistDTO, String token) {
    User user = userService.getUserByToken(token);
    playlistDAO.addPlaylist(user.getUsername(), playlistDTO);
    Playlists playlists = playlistDAO.findPlaylistsByUsername(user.getUsername());
    return new PlaylistsDTO(PLAYLIST_MAPPER.mapToAllPlaylistsResponseDTO(playlists), calculatePlaylistLength(playlists));
  }

  @Override
  public PlaylistsDTO deletePlaylist(int playlistId, String token) {
    String username = userService.getUserByToken(token).getUsername();
    playlistDAO.deletePlaylist(playlistId, username);
    Playlists playlists = playlistDAO.findPlaylistsByUsername(username);
    return new PlaylistsDTO(PLAYLIST_MAPPER.mapToAllPlaylistsResponseDTO(playlists), calculatePlaylistLength(playlists));
  }

  @Override
  public TracksDTO getTracks(int playlistId, String token) {
    String username = userService.getUserByToken(token).getUsername();
    List<Track> tracks = playlistDAO.getTracksFromPlaylist(playlistId, username);
    List<TrackDTO> trackDTOS = TRACK_MAPPER.mapToDTO(tracks);
    return new TracksDTO(trackDTOS);
  }

  @Override
  public TracksDTO addTrack(TrackDTO track, String token, int playlistId) {
    playlistDAO.addTrackToPlaylist(track, playlistId);
    return getTracks(playlistId, token);
  }

  @Override
  public TracksDTO deleteTrack(String token, int playlistID, int trackID) {
    playlistDAO.deleteTrackFromPlaylist(trackID);
    return getTracks(playlistID, token);
  }

  public int calculatePlaylistLength(Playlists playlists) {
      return playlists
        .getPlaylists()
        .stream()
        .map(Playlist::getTracks)
        .flatMap(Collection::stream)
        .mapToInt(Track::getDuration)
        .sum();
  }

  public void addTracksToPlaylists(Playlists playlists, String username){
     playlists.getPlaylists().forEach(playlist -> {
       System.out.println("NAME: " + playlist.getName());
      List<Track> tracks = playlistDAO.getTracksFromPlaylist(playlist.getId(), username);
      playlist.setTracks(tracks);
    });
  }

}
