package nl.han.oose.dea.spotitube.data_access.dao;

import jakarta.inject.Inject;
import nl.han.oose.dea.spotitube.business.exceptions.UnauthorizedException;
import nl.han.oose.dea.spotitube.data_access.data_mappers.UserMapper;
import nl.han.oose.dea.spotitube.data_access.models.User;
import nl.han.oose.dea.spotitube.data_access.util.DatabaseManager;
import nl.han.oose.dea.spotitube.interfaces.dao.UserDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {
  private final Connection CONNECTION;

  @Inject
  public UserDAOImpl(DatabaseManager databaseManager){
    this.CONNECTION = databaseManager.connect();
  }

  public UserDAOImpl(Connection CONNECTION) {
    this.CONNECTION = CONNECTION;
  }

  @Override
  public User findByUsername(String username) {
    User user = null;
    try {
      String FIND_BY_USERNAME_QUERY = "SELECT * FROM users WHERE username = ?";
      PreparedStatement statement = CONNECTION.prepareStatement(FIND_BY_USERNAME_QUERY);
      statement.setString(1, username);
      ResultSet resultSet = statement.executeQuery();

      if (resultSet != null) {
        if (resultSet.next()) {
          user = new UserMapper().map(resultSet);
        }
      }
    } catch (SQLException e){
      throw new RuntimeException(e);
    }
    return user;
  }

  @Override
  public void insertUser(User user) {
    try {
      PreparedStatement preparedStatement = CONNECTION.prepareStatement("INSERT INTO users (username, password, token) VALUES (?, ?, ?)");
      // Fill in the statement using UserMapper
      new UserMapper().map(user, preparedStatement);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public User findByToken(String token) {
    User user = null;
    try {
      PreparedStatement statement = CONNECTION.prepareStatement("SELECT * FROM users WHERE token = ?");
      statement.setString(1, token);
      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next()){
        user = new UserMapper().map(resultSet);
      } else {
          throw new UnauthorizedException("No user found with the provided token.");
      }
    } catch (SQLException e){
      throw new RuntimeException(e);
    }
    return user;
  }
}
