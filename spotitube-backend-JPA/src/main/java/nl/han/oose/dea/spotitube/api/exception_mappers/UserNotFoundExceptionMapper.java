package nl.han.oose.dea.spotitube.api.exception_mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import nl.han.oose.dea.spotitube.business.exceptions.UserNotFoundException;

@Provider
public class UserNotFoundExceptionMapper implements ExceptionMapper<UserNotFoundException> {
  @Override
  public Response toResponse(UserNotFoundException e) {
    return Response.status(UserNotFoundException.STATUSCODE).entity(e.getMessage()).build();
  }
}
