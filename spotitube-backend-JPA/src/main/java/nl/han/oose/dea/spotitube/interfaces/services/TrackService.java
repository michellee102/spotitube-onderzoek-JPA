package nl.han.oose.dea.spotitube.interfaces.services;

import nl.han.oose.dea.spotitube.business.dto.TracksDTO;

public interface TrackService {
  public TracksDTO getAvailableTracks(String token, int forPlaylistId);
}
