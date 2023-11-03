package nl.han.oose.dea.spotitube.data_access.performance;

import jakarta.persistence.EntityManager;
import nl.han.oose.dea.spotitube.data_access.models.Track;
import nl.han.oose.dea.spotitube.data_access.util.DatabaseManager;
import nl.han.oose.dea.spotitube.data_access.util.JPAUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PerformanceTester {

  private EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

  public void testJPAInserts(int numOfInserts){
    long startTime = System.currentTimeMillis();

    entityManager.getTransaction().begin();
    for (int i = 0; i < numOfInserts; i++){
      entityManager.persist(new Track("Shape of You", "Ed Sheeran", 233, "÷", 5832946, "2017-01-06", "Hit single from Ed Sheeran", true));
    }

    entityManager.getTransaction().commit();
    entityManager.close();

    long endTime = System.currentTimeMillis();
    long executionTime = endTime - startTime;

    System.out.println("JPA Insert Time for " + numOfInserts + " inserts: " + executionTime + " ms");
  }


  public void testJDBCInserts(int numberOfInserts) throws SQLException {
    long startTime = System.currentTimeMillis();

    try {
      Connection connection = new DatabaseManager().connect();
      String INSERT_DUMMY_TRACKS_QUERY = "INSERT INTO tracks (title, performer, duration, album, playcount, publicationDate, description, offlineAvailable) VALUES " +
        "('Shape of You', 'Ed Sheeran', 233, '÷', 5832946, '2017-01-06', 'Hit single from Ed Sheeran', true)";

      for (int i = 0; i < numberOfInserts; i++) {
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_DUMMY_TRACKS_QUERY);
        preparedStatement.executeUpdate();
      }
    } catch (SQLException e){
      System.out.println("couldnt insert");
    }
    long endTime = System.currentTimeMillis();
    long executionTime = endTime - startTime;

    System.out.println("JDBC Insert Time for " + numberOfInserts + " inserts: " + executionTime + " ms");
  }
}
