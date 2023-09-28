package nl.han.oose.dea.spotitube.data_access.data_mappers;

import nl.han.oose.dea.spotitube.business.dto.TrackDTO;
import nl.han.oose.dea.spotitube.data_access.models.Track;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrackMapper implements Mapper<Track> {
  @Override
  public Track map(ResultSet resultSet) throws SQLException {
    int id = resultSet.getInt("id");
    String title = resultSet.getString("title");
    String performer = resultSet.getString("performer");
    int duration = resultSet.getInt("duration");
    String album = resultSet.getString("album");
    int playcount = resultSet.getInt("playcount");
    String publicationDate = resultSet.getString("publicationDate");
    String description = resultSet.getString("description");
    boolean offlineAvailable = resultSet.getBoolean("offlineAvailable");
    return new Track(id, title, performer, duration, album, playcount, publicationDate, description, offlineAvailable);
  }

  @Override
  public void map(Track track, PreparedStatement preparedStatement) throws SQLException {

  }

  public List<TrackDTO> mapToDTO(List<Track> tracks) {
    return tracks.stream()
      .map(track -> new TrackDTO(
        track.getId(),
        track.getTitle(),
        track.getPerformer(),
        track.getDuration(),
        track.getAlbum(),
        track.getPlayCount(),
        track.getPublicationDate(),
        track.getDescription(),
        track.isOfflineAvailable()
      ))
      .toList();
  }

}
