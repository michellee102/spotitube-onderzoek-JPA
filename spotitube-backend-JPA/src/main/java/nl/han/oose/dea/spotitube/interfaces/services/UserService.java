package nl.han.oose.dea.spotitube.interfaces.services;

import nl.han.oose.dea.spotitube.business.exceptions.UnauthorizedException;
import nl.han.oose.dea.spotitube.data_access.models.User;
import nl.han.oose.dea.spotitube.interfaces.dao.UserDAO;

public interface UserService {
  public void setUserDAO(UserDAO userDAO);
    public String login(String username, String password) throws UnauthorizedException;
    public User createNewUser(String username, String password);
  public String verifyCredentials(User user, String password) throws UnauthorizedException;
  public Boolean verifyToken(String token);
  public User getUserByToken(String token);

}
