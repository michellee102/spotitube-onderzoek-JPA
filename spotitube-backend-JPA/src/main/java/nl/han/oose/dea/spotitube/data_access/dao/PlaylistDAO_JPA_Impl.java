package nl.han.oose.dea.spotitube.data_access.dao;

import jakarta.enterprise.inject.Default;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import nl.han.oose.dea.spotitube.JPAUtil;
import nl.han.oose.dea.spotitube.business.dto.PlaylistDTO;
import nl.han.oose.dea.spotitube.business.dto.TrackDTO;
import nl.han.oose.dea.spotitube.business.exceptions.CouldNotAddPlaylistException;
import nl.han.oose.dea.spotitube.business.exceptions.PlaylistNotFoundException;
import nl.han.oose.dea.spotitube.data_access.models.Playlist;
import nl.han.oose.dea.spotitube.data_access.models.Playlists;
import nl.han.oose.dea.spotitube.data_access.models.Track;
import nl.han.oose.dea.spotitube.interfaces.dao.PlaylistDAO;

import java.util.ArrayList;
import java.util.List;

@Default
public class PlaylistDAO_JPA_Impl implements PlaylistDAO {

  private EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

  @Override
  public Playlists findPlaylistsByUsername(String username) {
    List<Playlist> playlists;
    try {
      entityManager.getTransaction().begin();
      TypedQuery<Playlist> query = entityManager.createQuery("SELECT p FROM Playlist p WHERE p.username = :username", Playlist.class);
      query.setParameter("username", username);
      playlists = query.getResultList();
//      playlists.forEach(playlist -> playlist.setTracks(new ArrayList<>()));
      return new Playlists(playlists);
    } catch (NoResultException e) {
      throw new PlaylistNotFoundException("Could not find any playlists by the username " + username);
    } finally {
      entityManager.getTransaction().commit();
    }
  }

  @Override
  public void updateName(int playlistId, String newName, String ownersName) throws PlaylistNotFoundException {
    try {
      entityManager.getTransaction().begin();
      Query query = entityManager.createQuery("UPDATE Playlist SET name = :name WHERE username = :username AND id = :id", Playlist.class);
      query.setParameter("name", newName);
      query.setParameter("username", ownersName);
      query.setParameter("id", playlistId);
      query.executeUpdate();
    } catch (NoResultException e) {
      throw new PlaylistNotFoundException("No playlist found with the given id.");
    } finally {
      entityManager.getTransaction().commit();
    }
  }

  @Override
  public void addPlaylist(String username, PlaylistDTO playlistDTO) {
    try {
      entityManager.getTransaction().begin();
      Playlist playlist = new Playlist(playlistDTO.getName(), playlistDTO.isOwner(), username);
      entityManager.persist(playlist);
    } catch (NoResultException e) {
      throw new RuntimeException(e);
    } finally {
      entityManager.getTransaction().commit();
    }
  }

  @Override
  public void deletePlaylist(int playlistId, String username) {

  }

  @Override
  public List<Track> getTracksFromPlaylist(int playlistId, String username) {
    List<Track> tracks;
    try {
      entityManager.getTransaction().begin();
      TypedQuery<Track> query = entityManager.createQuery("SELECT t FROM Track t JOIN t.playlist p WHERE p.id = :playlistId AND p.username = :username", Track.class);
      query.setParameter("playlistId", playlistId);
      query.setParameter("username", username);
      tracks = query.getResultList();
    } catch (NoResultException e) {
      throw new RuntimeException(e);
    } finally {
      entityManager.getTransaction().commit();
    }
    return tracks;
  }

  @Override
  public void addTrackToPlaylist(TrackDTO track, int playlistId) {

  }

  @Override
  public void deleteTrackFromPlaylist(int trackID) {

  }
}
