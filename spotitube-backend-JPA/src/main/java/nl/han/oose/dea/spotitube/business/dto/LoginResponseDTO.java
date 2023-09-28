package nl.han.oose.dea.spotitube.business.dto;

public class LoginResponseDTO {
  private String token;
  private String user;

  public LoginResponseDTO(String token, String user) {
    this.user = user;
    this.token = token;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }
}
