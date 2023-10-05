package nl.han.oose.dea.spotitube;

import nl.han.oose.dea.spotitube.business.dto.JPA.User;
import org.hibernate.Session;

import java.util.List;

public class APp {

  public static SetupSessionFactory sessionFactoryManager = new SetupSessionFactory();

  // De session houdt in feite de verbinding met de db.

  public static void main(String[] args) {
    // Setup de verbinding met db
    sessionFactoryManager.setUp();

//    save_user_to_db();
      fetch_users_with_hql();
  }

  public static void save_user_to_db(){
    // Maak object aan die je wil opslaan
    User testUser = new User("miessschelle", "fdsfdsfds", "password9324324");

    // Start de verbinding
    // session: Hetzelfde als EntityManager in JPA
    try (Session session = sessionFactoryManager.getSessionFactory().openSession()) {
      // begin transactie
      session.beginTransaction();

      // Sla object op in db
      session.persist(testUser);

      // Beëindig transactie
      session.getTransaction().commit();
    }
  }

  public static void fetch_users_with_hql(){
    // Start de verbinding
    try (Session session = sessionFactoryManager.getSessionFactory().openSession()) {
      // begin transactie
      session.beginTransaction();

      // Vraag alle users op die bestaan in de db van je User object
      List<User> users = session.createQuery("select u from User u", User.class).stream().toList();

      users.forEach(user -> System.out.println("Usernames: " + user.getUser()));

      // Beëindig transactie
      session.getTransaction().commit();
    }
  }
}
