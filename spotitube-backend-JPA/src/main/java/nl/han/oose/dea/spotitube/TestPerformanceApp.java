package nl.han.oose.dea.spotitube;

import jakarta.persistence.EntityManager;
import nl.han.oose.dea.spotitube.data_access.PerformanceTester;
import nl.han.oose.dea.spotitube.data_access.dao.UserDAO_JPA_Impl;
import nl.han.oose.dea.spotitube.data_access.util.JPAUtil;

import java.sql.SQLException;

public class TestPerformanceApp {
  public static void main(String[] args) throws SQLException {
    PerformanceTester performanceTester = new PerformanceTester();

//    performanceTester.testJPAInserts(1);
//    performanceTester.testJDBCInserts(1);
//
//    performanceTester.testJPAInserts(10);
//    performanceTester.testJDBCInserts(10);

//
//    performanceTester.testJPAInserts(50);
//    performanceTester.testJDBCInserts(50);
//
//    performanceTester.testJPAInserts(100);
//    performanceTester.testJDBCInserts(100);
//
//    performanceTester.testJPAInserts(200);
//    performanceTester.testJDBCInserts(200);

//    performanceTester.testJPAInserts(500);
//    performanceTester.testJDBCInserts(500);

//    performanceTester.testJPAInserts(1000);
//    performanceTester.testJDBCInserts(1000);
  }


}
