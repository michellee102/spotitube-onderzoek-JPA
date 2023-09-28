package nl.han.oose.dea.spotitube.business.services;

import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import nl.han.oose.dea.spotitube.crosscutting_concerns.Authentication;
import nl.han.oose.dea.spotitube.data_access.models.User;
import nl.han.oose.dea.spotitube.interfaces.dao.UserDAO;
import nl.han.oose.dea.spotitube.interfaces.services.UserService;

import java.util.UUID;

@Default
public class UserServiceImpl implements UserService {
  private UserDAO userDAO;
  private Authentication authentication;

  @Inject
  public void setUserDAO(UserDAO userDAO) {
    this.userDAO = userDAO;
  }

  @Inject
  public void setAuthentication(Authentication authentication) {this.authentication = authentication;}

  @Override
  public String login(String username, String password)  {
    User existingUser = userDAO.findByUsername(username);
    if (existingUser != null){
      return verifyCredentials(existingUser, password);
    } else {
      User newUser = createNewUser(username, password);
      return newUser.getToken();
    }
  }

  public User createNewUser(String username, String password){
    String token = UUID.randomUUID().toString();
    User newUser = new User(username, authentication.hashPassword(password), token);
    userDAO.insertUser(newUser);
    return newUser;
  }

  public String verifyCredentials(User user, String password) {
    authentication.checkPassword(password, user.getPassword());
    return user.getToken();
  }

  public Boolean verifyToken(String token)  {
    User user = userDAO.findByToken(token);
    return (user != null);
  }

  public User getUserByToken(String token){
    if(verifyToken(token)) {
      return userDAO.findByToken(token);
    }
    return null;
  }

}
