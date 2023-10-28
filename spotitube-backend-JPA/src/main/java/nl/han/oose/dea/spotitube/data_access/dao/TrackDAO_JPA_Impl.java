package nl.han.oose.dea.spotitube.data_access.dao;

import jakarta.enterprise.inject.Default;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import nl.han.oose.dea.spotitube.data_access.util.JPAUtil;
import nl.han.oose.dea.spotitube.data_access.models.Track;
import nl.han.oose.dea.spotitube.interfaces.dao.TrackDAO;

import java.util.List;

@Default
public class TrackDAO_JPA_Impl implements TrackDAO {
  private EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
  @Override
  public List<Track> getAvailableTracks(int playlistId, String username) {
    List<Track> tracks;
    try {
      entityManager.getTransaction().begin();
      TypedQuery<Track> query = entityManager.createQuery("SELECT t FROM Track t WHERE t.playlist.id != :playlistId OR t.playlist.id IS NULL", Track.class);
      query.setParameter("playlistId", playlistId);
      tracks = query.getResultList();
    } catch (NoResultException e) {
      throw new RuntimeException(e);
    } finally {
      entityManager.getTransaction().commit();
    }
    return tracks;
  }

  @Override
  public void findTrackById(int id) {

  }
}
