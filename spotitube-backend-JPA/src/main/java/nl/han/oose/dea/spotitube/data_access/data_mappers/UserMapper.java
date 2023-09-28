package nl.han.oose.dea.spotitube.data_access.data_mappers;

import nl.han.oose.dea.spotitube.data_access.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements Mapper<User>{

  @Override
  public User map(ResultSet resultSet) throws SQLException {
    String USERNAME_COLUMN = "username";
    String PASSWORD_COLUMN = "password";
    String TOKEN_COLUMN = "token";

    String username = resultSet.getString(USERNAME_COLUMN);
    String password = resultSet.getString(PASSWORD_COLUMN);
    String token = resultSet.getString(TOKEN_COLUMN);
    return new User(username, password, token);
  }

  @Override
  public void map(User user, PreparedStatement preparedStatement) throws SQLException {
    preparedStatement.setString(1, user.getUsername());
    preparedStatement.setString(2, user.getPassword());
    preparedStatement.setString(3, user.getToken());
  }
}
