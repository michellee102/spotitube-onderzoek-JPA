package nl.han.oose.dea.spotitube;

import nl.han.oose.dea.spotitube.business.dto.JPA.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import static java.lang.System.out;

public class TestApp {

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
      // The registry would be destroyed by the SessionFactory, but we
      // had trouble building the SessionFactory so destroy it manually.
      StandardServiceRegistryBuilder.destroy(registry);
      e.printStackTrace();
    }
  }


  protected void test(){
    sessionFactory.inTransaction(session -> {
      session.persist(new User("bla", "fdsfdsfdsf", "hii"));
    });
  }

  protected void getData(){
    sessionFactory.inTransaction(session -> {
      session.createSelectionQuery("from User", User.class)
        .getResultList()
        .forEach(user -> out.println("User (" + user.getUser() + ") : " + user.getToken()));
    });
  }





}
