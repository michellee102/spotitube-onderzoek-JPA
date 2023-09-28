package nl.han.oose.dea.spotitube.business.services;

import jakarta.inject.Inject;
import nl.han.oose.dea.spotitube.business.dto.TrackDTO;
import nl.han.oose.dea.spotitube.business.dto.TracksDTO;
import nl.han.oose.dea.spotitube.interfaces.dao.TrackDAO;
import nl.han.oose.dea.spotitube.data_access.data_mappers.TrackMapper;
import nl.han.oose.dea.spotitube.interfaces.services.TrackService;

import java.util.List;

public class TrackServiceImpl implements TrackService {
  private TrackDAO trackDAO;
  private final TrackMapper TRACK_MAPPER = new TrackMapper();

  @Inject
  public void setTrackDAO(TrackDAO trackDAO) {
    this.trackDAO = trackDAO;
  }

  @Override
  public TracksDTO getAvailableTracks(String token, int forPlaylistId) {
    List<TrackDTO> trackDTOS = TRACK_MAPPER.mapToDTO(trackDAO.getAvailableTracks(forPlaylistId, token));
    return new TracksDTO(trackDTOS);
  }
}
