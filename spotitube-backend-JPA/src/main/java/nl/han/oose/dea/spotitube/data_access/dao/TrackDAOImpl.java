package nl.han.oose.dea.spotitube.data_access.dao;

import jakarta.inject.Inject;
import nl.han.oose.dea.spotitube.business.exceptions.TrackNotFoundException;
import nl.han.oose.dea.spotitube.data_access.models.Track;
import nl.han.oose.dea.spotitube.data_access.data_mappers.TrackMapper;
import nl.han.oose.dea.spotitube.data_access.util.DatabaseManager;
import nl.han.oose.dea.spotitube.interfaces.dao.TrackDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrackDAOImpl implements TrackDAO {
  private final Connection CONNECTION;

  @Inject
  public TrackDAOImpl(DatabaseManager databaseManager) {
    this.CONNECTION = databaseManager.connect();
  }

  public TrackDAOImpl(Connection CONNECTION) {
    this.CONNECTION = CONNECTION;
  }

  public Connection getCONNECTION() {
    return CONNECTION;
  }

  @Override
  public List<Track> getAvailableTracks(int playlistId, String username) {
    List<Track> tracks = new ArrayList<>();
    try {
      String SELECT_AVAILABLE_TRACKS_QUERY = "SELECT * FROM tracks WHERE playlist_id != ? OR playlist_id IS NULL";
      PreparedStatement preparedStatement = CONNECTION.prepareStatement(SELECT_AVAILABLE_TRACKS_QUERY);
      preparedStatement.setInt(1, playlistId);
      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        Track track = new TrackMapper().map(resultSet);
        tracks.add(track);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return tracks;
  }

  @Override
  public void findTrackById(int id) {
    try {
      String SELECT_TRACK_QUERY = "SELECT * FROM tracks WHERE id = ?";
      PreparedStatement preparedStatement = CONNECTION.prepareStatement(SELECT_TRACK_QUERY);
      preparedStatement.setInt(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (!resultSet.next()) {
        throw new TrackNotFoundException("Could not find track with id " + id);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

}
