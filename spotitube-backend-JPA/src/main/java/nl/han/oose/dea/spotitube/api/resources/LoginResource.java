package nl.han.oose.dea.spotitube.api.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.han.oose.dea.spotitube.business.dto.LoginRequestDTO;
import nl.han.oose.dea.spotitube.business.dto.LoginResponseDTO;
import nl.han.oose.dea.spotitube.interfaces.services.UserService;


@Path("/login")
public class LoginResource {
  private UserService userService;

  @Inject
  public void setUserService(UserService userService){
    this.userService = userService;
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response authenticateUser(LoginRequestDTO loginRequestDTO)  {
    String token = userService.login(
      loginRequestDTO.getUser(),
      loginRequestDTO.getPassword()
    );
      LoginResponseDTO response = new LoginResponseDTO(token, loginRequestDTO.getUser());
      return Response
        .ok()
        .entity(response)
        .build();
  }
}
