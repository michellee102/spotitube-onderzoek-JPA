package nl.han.oose.dea.spotitube.api.exception_mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import nl.han.oose.dea.spotitube.business.exceptions.UnauthorizedException;

@Provider
public class UnauthorizedExceptionMapper implements ExceptionMapper<UnauthorizedException> {
  @Override
  public Response toResponse(UnauthorizedException e) {
    return Response.status(UnauthorizedException.STATUSCODE).entity(e.getMessage()).build();
  }
}
