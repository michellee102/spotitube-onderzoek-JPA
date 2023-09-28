package nl.han.oose.dea.spotitube.interfaces.services;

import nl.han.oose.dea.spotitube.business.dto.PlaylistsDTO;
import nl.han.oose.dea.spotitube.business.dto.PlaylistDTO;
import nl.han.oose.dea.spotitube.business.dto.TrackDTO;
import nl.han.oose.dea.spotitube.business.dto.TracksDTO;

public interface PlaylistService {
  public PlaylistsDTO getPlaylists(String token);

  public PlaylistsDTO updatePlaylistName(int playlistId, String token, PlaylistDTO playlistDTO) ;

  public PlaylistsDTO addPlaylist(PlaylistDTO playlistDTO, String token) ;

  public PlaylistsDTO deletePlaylist(int playlistId, String token);

  public TracksDTO getTracks(int playlistId, String token);

  public TracksDTO addTrack(TrackDTO track, String token, int playlistID);

  public TracksDTO deleteTrack(String token, int playlistID, int trackID);
}
