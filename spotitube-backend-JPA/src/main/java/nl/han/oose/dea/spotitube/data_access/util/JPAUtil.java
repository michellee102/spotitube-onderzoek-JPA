package nl.han.oose.dea.spotitube.data_access.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

  private static final String PERSISTENCE_UNIT_NAME = "spotitube-jpa";
  private static EntityManagerFactory entityManagerFactory ;

  public static EntityManagerFactory getEntityManagerFactory() {
    if (entityManagerFactory  == null) {
      entityManagerFactory  = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }
    return entityManagerFactory ;
  }

}
