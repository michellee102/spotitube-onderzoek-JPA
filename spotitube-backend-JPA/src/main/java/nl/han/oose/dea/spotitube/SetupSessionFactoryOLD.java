package nl.han.oose.dea.spotitube;

import nl.han.oose.dea.spotitube.data_access.models.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class SetupSessionFactoryOLD {
  // Hetzelfde als EntityManagerFactory in JPA
  private SessionFactory sessionFactory;

  protected void setUp() {
    // A SessionFactory is set up once for an application!
    final StandardServiceRegistry registry =
      new StandardServiceRegistryBuilder()
        .configure() // Hiermee wordt de standaard Hibernate-configuratie gelezen
        .build();
    try {
      MetadataSources metadataSources =
        new MetadataSources(registry)
          .addAnnotatedClass(User.class);
      Metadata metadata = metadataSources.buildMetadata();
      sessionFactory = metadata.buildSessionFactory();
    } catch (Exception e) {
      StandardServiceRegistryBuilder.destroy(registry);
      e.printStackTrace();
    }
  }

  public SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  //  protected void test(){
//    sessionFactory.inTransaction(session -> {
//      session.persist(new User("bla", "fdsfdsfdsf", "hii"));
//    });
//  }
//
//  protected void getData(){
//    sessionFactory.inTransaction(session -> {
//      session.createSelectionQuery("from User", User.class)
//        .getResultList()
//        .forEach(user -> out.println("User (" + user.getUser() + ") : " + user.getToken()));
//    });
//  }





}