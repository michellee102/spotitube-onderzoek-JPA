package nl.han.oose.dea.spotitube;

import nl.han.oose.dea.spotitube.business.dto.JPA.User;
import org.hibernate.Session;

public class APp {

  public static SetupSessionFactory sessionFactoryManager = new SetupSessionFactory();

  // De session houdt in feite de verbinding met de db.

  public static void main(String[] args) {
    // Setup de verbinding met db
    sessionFactoryManager.setUp();

    // Maak object aan die je wil opslaan
    User testUser = new User("miechelle", "fdsfdsfds", "password9324324");

    // Start de verbinding
    try (Session session = sessionFactoryManager.getSessionFactory().openSession()) {
      // begin transactie
      session.beginTransaction();

      // Sla object op in db
      session.persist(testUser);

      // Beëindig transactie
      session.getTransaction().commit();
    }
  }
}
