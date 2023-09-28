package nl.han.oose.dea.spotitube.data_access.models;

public class User {
  private int id;
  private String username;
  private String password;
  private String token;

  public User(String username, String password, String token) {
    this.username = username;
    this.password = password;
    this.token = token;
  }

  public User(String username) {
    this.username = username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getToken() {
    return token;
  }

}
