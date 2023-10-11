package nl.han.oose.dea.spotitube.data_access.dao;

import jakarta.enterprise.inject.Alternative;
import jakarta.enterprise.inject.Default;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import nl.han.oose.dea.spotitube.JPAUtil;
import nl.han.oose.dea.spotitube.data_access.models.User;
import nl.han.oose.dea.spotitube.interfaces.dao.UserDAO;

@Default
public class UserDAO_JPA_Impl implements UserDAO {

  private EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

  @Override
  public User findByUsername(String username) {
    // Begin een transactie
    entityManager.getTransaction().begin();
    // Voer een veilige parameterized query uit om SQL-injectie te voorkomen
    TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
    query.setParameter("username", username);
    User user = null;
    try {
      // Probeer de gebruiker op te halen
      user = query.getSingleResult();
      System.out.println(user.getUsername());
      System.out.println(user.getToken());
      return user;
    } catch (NoResultException e) {
      // Geen gebruiker gevonden met de opgegeven gebruikersnaam
      return null;
    } finally {
      // Sluit de transactie af
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
    // Begin een transactie
    entityManager.getTransaction().begin();
    // Voer een veilige parameterized query uit om SQL-injectie te voorkomen
    TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.token = :token", User.class);
    query.setParameter("token", token);
    User user = null;
    try {
      // Probeer de gebruiker op te halen
      user = query.getSingleResult();
      System.out.println(user.getUsername());
      return user;
    } catch (NoResultException e) {
      // Geen gebruiker gevonden met de opgegeven gebruikersnaam
      return null;
    } finally {
      // Sluit de transactie af
      entityManager.getTransaction().commit();
    }
  }
}
