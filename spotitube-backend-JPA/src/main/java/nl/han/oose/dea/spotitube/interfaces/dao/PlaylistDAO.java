package nl.han.oose.dea.spotitube.interfaces.dao;

import nl.han.oose.dea.spotitube.business.dto.PlaylistDTO;
import nl.han.oose.dea.spotitube.business.dto.TrackDTO;
import nl.han.oose.dea.spotitube.business.exceptions.CouldNotAddPlaylistException;
import nl.han.oose.dea.spotitube.business.exceptions.PlaylistNotFoundException;
import nl.han.oose.dea.spotitube.data_access.models.Playlists;
import nl.han.oose.dea.spotitube.data_access.models.Track;

import java.util.List;

public interface PlaylistDAO {
  public Playlists findPlaylistsByUsername(String username);
  public void updateName(int playlistId, String newName, String ownersName) throws PlaylistNotFoundException;
  public void addPlaylist(String username, PlaylistDTO playlistDTO) throws CouldNotAddPlaylistException;
  public void deletePlaylist(int playlistId, String username);
  public List<Track> getTracksFromPlaylist(int playlistId, String username);

  public void addTrackToPlaylist(TrackDTO track, int playlistId);

  public void deleteTrackFromPlaylist(int trackID);
}
