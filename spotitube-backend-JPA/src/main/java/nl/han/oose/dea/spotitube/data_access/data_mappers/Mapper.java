package nl.han.oose.dea.spotitube.data_access.data_mappers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface Mapper<T> {

  // Zet resultset object om naar een entity object
  T map(ResultSet resultSet) throws SQLException;

  // Zet entity object om naar een preparedstatement object voor het opslaan van gegevens in db
  void map(T object, PreparedStatement preparedStatement) throws SQLException;
}
