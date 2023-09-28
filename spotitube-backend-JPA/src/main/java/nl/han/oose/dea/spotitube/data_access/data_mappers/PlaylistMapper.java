package nl.han.oose.dea.spotitube.data_access.data_mappers;

import nl.han.oose.dea.spotitube.business.dto.PlaylistDTO;
import nl.han.oose.dea.spotitube.data_access.models.Playlist;
import nl.han.oose.dea.spotitube.data_access.models.Playlists;
import nl.han.oose.dea.spotitube.data_access.models.Track;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlaylistMapper implements Mapper<Playlist>{

  private TrackMapper trackMapper = new TrackMapper();
    @Override
    public Playlist map(ResultSet resultSet) throws SQLException {
      int id = resultSet.getInt("id");
      String name = resultSet.getString("name");
      boolean owner = resultSet.getBoolean("owner");
      String username = resultSet.getString("username");
      List<Track> track = new ArrayList<>();
      return new Playlist(id, name, owner, username, track);
    }

    @Override
    public void map(Playlist playlist, PreparedStatement preparedStatement) throws SQLException {
    }

  public List<PlaylistDTO> mapToAllPlaylistsResponseDTO(Playlists playlists){
    return
    playlists
      .getPlaylists()
      .stream()
      .map(playlist -> new PlaylistDTO(
        playlist.getId(),
        playlist.getName(),
        playlist.isOwner(),
        trackMapper.mapToDTO(playlist.getTracks()))
      ).collect(Collectors.toList());
  }

}
