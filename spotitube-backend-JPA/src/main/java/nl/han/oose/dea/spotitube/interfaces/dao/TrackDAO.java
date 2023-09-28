package nl.han.oose.dea.spotitube.interfaces.dao;

import nl.han.oose.dea.spotitube.data_access.models.Track;

import java.util.List;

public interface TrackDAO {
  public List<Track> getAvailableTracks(int playlistId, String username);

  public void findTrackById(int id);
}
