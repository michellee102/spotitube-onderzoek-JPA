package nl.han.oose.dea.spotitube;

import jakarta.persistence.EntityManager;
import nl.han.oose.dea.spotitube.data_access.dao.UserDAO_JPA_Impl;
import nl.han.oose.dea.spotitube.data_access.models.User;
import org.hibernate.Session;

import java.util.List;

public class APp {

  public static SetupSessionFactoryOLD sessionFactoryManagerOld = new SetupSessionFactoryOLD();

  // Dit is de manager van alle entities
  public static EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
  public static UserDAO_JPA_Impl userDAOJpa = new UserDAO_JPA_Impl();

  public static void main(String[] args) {
    // Setup de verbinding met db
//    sessionFactoryManagerOld.setUp();

//    save_user_to_db_old();
//      fetch_users_with_hql();

//    save_user_to_db();
//    get_users_db();
//    userDAOJpa.findByUsername("jpa");
//    userDAOJpa.findByToken("fdsfdsfdsf");
//    save_user_to_db();
    userDAOJpa.insertUser(new User("hi", "token-sdfdsfds-sdf", "&2637GHJf7"));
  }


//  public static void save_user_to_db(){
//    // Maak object aan die je wil opslaan
//    User testUser = new User("dsdasdsadasdasdas", "jojo", "hshs");
//
//    // start de transactie
//    entityManager.getTransaction().begin();
//
//    // sla user op in db
//    entityManager.persist(testUser);
//
//    // beeinding de transactier
//    entityManager.getTransaction().commit();
//
//  }
//
//  public static void get_users_db(){
//    // start de transactie
//    entityManager.getTransaction().begin();
//
//    // sla user op in db
//    List<User> users = entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
//
//    users.forEach(user -> System.out.println(user.getUser()));
//
//    // beeinding de transactier
//    entityManager.getTransaction().commit();
//    entityManager.close();
//  }
//  public static void save_user_to_db_old(){
//    // Maak object aan die je wil opslaan
//    User testUser = new User("jaja", "jojo", "hshs");
//
//    // Start de verbinding
//    // session: Hetzelfde als EntityManager in JPA
//    try (Session session = sessionFactoryManagerOld.getSessionFactory().openSession()) {
//      // begin transactie
//      session.beginTransaction();
//
//      // Sla object op in db
//      session.persist(testUser);
//
//      // Beëindig transactie
//      session.getTransaction().commit();
//    }
//  }

//  public static void fetch_users_with_hql(){
//    // Start de verbinding
//    try (Session session = sessionFactoryManagerOld.getSessionFactory().openSession()) {
//      // begin transactie
//      session.beginTransaction();
//
//      // Vraag alle users op die bestaan in de db van je User object
//      List<User> users = session.createQuery("select u from User u", User.class).stream().toList();
//
//      users.forEach(user -> System.out.println("Usernames: " + user.getUser()));
//
//      // Beëindig transactie
//      session.getTransaction().commit();
//    }
//  }
//}

}
