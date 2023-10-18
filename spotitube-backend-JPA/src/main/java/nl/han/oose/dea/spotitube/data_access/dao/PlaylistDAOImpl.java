package nl.han.oose.dea.spotitube.data_access.dao;

import jakarta.enterprise.inject.Alternative;
import jakarta.inject.Inject;
import nl.han.oose.dea.spotitube.business.dto.PlaylistDTO;
import nl.han.oose.dea.spotitube.business.dto.TrackDTO;
import nl.han.oose.dea.spotitube.business.exceptions.PlaylistNotFoundException;
import nl.han.oose.dea.spotitube.data_access.models.Playlist;
import nl.han.oose.dea.spotitube.data_access.models.Playlists;
import nl.han.oose.dea.spotitube.data_access.models.Track;
import nl.han.oose.dea.spotitube.data_access.data_mappers.PlaylistMapper;
import nl.han.oose.dea.spotitube.data_access.data_mappers.TrackMapper;
import nl.han.oose.dea.spotitube.data_access.util.DatabaseManager;
import nl.han.oose.dea.spotitube.interfaces.dao.PlaylistDAO;
import nl.han.oose.dea.spotitube.interfaces.dao.TrackDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@Alternative
public class PlaylistDAOImpl implements PlaylistDAO {
  private final Connection CONNECTION;
  private final Logger LOGGER = Logger.getLogger(getClass().getName());

  private TrackDAO trackDAO;
  @Inject
  public PlaylistDAOImpl(DatabaseManager databaseManager) {
    this.CONNECTION = databaseManager.connect();
  }

  public PlaylistDAOImpl(Connection connection) {
    this.CONNECTION = connection;
  }

  @Inject
  public void setTrackDAO(TrackDAO trackDAO) {
    this.trackDAO = trackDAO;
  }

  public TrackDAO getTrackDAO() {
    return trackDAO;
  }

  @Override
  public Playlists
  findPlaylistsByUsername(String username) {
    List<Playlist> playlists = new ArrayList<>();
    try {
      String GET_PLAYLISTS_QUERY = "SELECT * FROM playlists WHERE username = ?";
      PreparedStatement preparedStatement = CONNECTION.prepareStatement(GET_PLAYLISTS_QUERY);
      preparedStatement.setString(1, username);
      ResultSet resultSet = preparedStatement.executeQuery();

      if(resultSet != null){
        while (resultSet.next()) {
          Playlist playlist = new PlaylistMapper().map(resultSet);
          playlists.add(playlist);
        }
      }
    } catch (SQLException e) {
      LOGGER.log(Level.SEVERE, "Could not find playlists.", e);
    }
    return new Playlists(playlists);
  }

  public boolean findPlaylistById(int id) {
    try {
      String SELECT_PLAYLIST_BY_ID_QUERY = "SELECT * FROM playlists WHERE id = ?";
      PreparedStatement preparedStatement = CONNECTION.prepareStatement(SELECT_PLAYLIST_BY_ID_QUERY);
      preparedStatement.setInt(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();

      if (resultSet == null) {
        throw new PlaylistNotFoundException("Could not find playlist with id " + id);
      }
    } catch (SQLException e) {
      LOGGER.log(Level.SEVERE, "Could not find playlist.", e);
    }
    return true;
  }



  @Override
  public void updateName(int playlistId, String newName, String ownersName) throws PlaylistNotFoundException {
    try {
      String UPDATE_NAME_QUERY = "UPDATE playlists SET name = ? WHERE username = ? AND id = ?";
      PreparedStatement preparedStatement = CONNECTION.prepareStatement(UPDATE_NAME_QUERY);
      preparedStatement.setString(1, newName);
      preparedStatement.setString(2, ownersName);
      preparedStatement.setInt(3, playlistId);
      int rowsUpdated = preparedStatement.executeUpdate();
      if (rowsUpdated == 0) {
        throw new PlaylistNotFoundException("No playlist found with the given id.");
      }
    } catch (SQLException e) {
      LOGGER.log(Level.SEVERE, "Kon afspeellijst niet bijwerken.", e);
    }
  }

  @Override
  public void addPlaylist(String username, PlaylistDTO playlistDTO) {
    try {
      String ADD_PLAYLIST_QUERY = "INSERT INTO playlists (name, owner, username) VALUES (?, ?, ?)";
      PreparedStatement preparedStatement = CONNECTION.prepareStatement(ADD_PLAYLIST_QUERY, Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setString(1, playlistDTO.getName());
      preparedStatement.setBoolean(2, true);
      preparedStatement.setString(3, username);
      preparedStatement.executeUpdate();

    } catch (SQLException e) {
      LOGGER.log(Level.SEVERE, "Kon afspeellijst niet toevoegen.", e);
    }
  }

  @Override
  public void deletePlaylist(int playlistId, String username) {
    try {
      String DELETE_QUERY = "DELETE FROM playlists WHERE id = ? AND username = ?";
      PreparedStatement preparedStatement = CONNECTION.prepareStatement(DELETE_QUERY);
      preparedStatement.setInt(1, playlistId);
      preparedStatement.setString(2, username);
      int deletedRows = preparedStatement.executeUpdate();
      if (deletedRows == 0) {
        throw new PlaylistNotFoundException("Could not find playlist with id " + playlistId);
      }
    } catch (SQLException e) {
      LOGGER.log(Level.SEVERE, "Kon afspeellijst niet verwijderen.", e);
    }
  }

  @Override
  public List<Track> getTracksFromPlaylist(int playlistId, String username) {
    List<Track> tracks = new ArrayList<>();
    try {
      String GET_TRACKS_QUERY = "SELECT tracks.* FROM tracks JOIN playlists ON tracks.playlist_id = playlists.id JOIN users ON playlists.username = users.username WHERE users.username = ? AND playlists.id = ?";
      PreparedStatement preparedStatement = CONNECTION.prepareStatement(GET_TRACKS_QUERY);
      preparedStatement.setString(1, username);
      preparedStatement.setInt(2, playlistId);
      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        Track track = new TrackMapper().map(resultSet);
        tracks.add(track);
      }
    } catch (SQLException e) {
      LOGGER.log(Level.SEVERE, "Kon tracks niet ophalen.", e);
    }
    return tracks;
  }

  @Override
  public void addTrackToPlaylist(TrackDTO track, int playlistId) {
    try {
      this.findPlaylistById(playlistId);
      trackDAO.findTrackById(track.getId());
      String ADD_TRACK_TO_PLAYLIST_QUERY = "UPDATE tracks SET playlist_id = ?, offlineAvailable = ? WHERE id = ?";
      PreparedStatement preparedStatement = CONNECTION.prepareStatement(ADD_TRACK_TO_PLAYLIST_QUERY);
      preparedStatement.setInt(1, playlistId);
      preparedStatement.setBoolean(2, track.isOfflineAvailable());
      preparedStatement.setInt(3, track.getId());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      LOGGER.log(Level.SEVERE, "Kon track niet toevoegen aan de afspeellijst.", e);
    }
  }

  @Override
  public void deleteTrackFromPlaylist(int trackID) {
    try {
      trackDAO.findTrackById(trackID);
      String DELETE_TRACK_FROM_PLAYLIST_QUERY = "UPDATE tracks SET playlist_id = NULL WHERE id = ?";
      PreparedStatement preparedStatement = CONNECTION.prepareStatement(DELETE_TRACK_FROM_PLAYLIST_QUERY);
      preparedStatement.setInt(1, trackID);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      LOGGER.log(Level.SEVERE, "Kon track niet toevoegen aan de afspeellijst.", e);
    }
  }


}
