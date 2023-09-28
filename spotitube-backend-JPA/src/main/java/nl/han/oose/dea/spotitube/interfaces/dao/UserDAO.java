package nl.han.oose.dea.spotitube.interfaces.dao;

import nl.han.oose.dea.spotitube.data_access.models.User;

public interface UserDAO {
  public User findByUsername(String username);
  public void insertUser(User user);

  public User findByToken(String token);
}
