package nl.han.oose.dea.spotitube.data_access.dao;

import jakarta.enterprise.inject.Default;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import nl.han.oose.dea.spotitube.data_access.util.JPAUtil;
import nl.han.oose.dea.spotitube.business.exceptions.UnauthorizedException;
import nl.han.oose.dea.spotitube.data_access.models.User;
import nl.han.oose.dea.spotitube.interfaces.dao.UserDAO;

@Default
public class UserDAO_JPA_Impl implements UserDAO {

  private EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

  @Override
  public User findByUsername(String username) {
    User user = null;
    try {
      entityManager.getTransaction().begin();
      TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
      query.setParameter("username", username);
      user = query.getSingleResult();
      return user;
    } catch (NoResultException e) {
      throw new RuntimeException(e);
    } finally {
      entityManager.getTransaction().commit();
    }
  }


  @Override
  public void insertUser(User user) {
    entityManager.getTransaction().begin();
    entityManager.persist(user);
    entityManager.getTransaction().commit();
  }

  @Override
  public User findByToken(String token) {
    User user;
    try {
      entityManager.getTransaction().begin();
      TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.token = :token", User.class);
      query.setParameter("token", token);
      user = query.getSingleResult();
      return user;
    } catch (NoResultException e) {
      throw new UnauthorizedException("No user found with the provided token.");
    } finally {
      entityManager.getTransaction().commit();
    }
  }
}
