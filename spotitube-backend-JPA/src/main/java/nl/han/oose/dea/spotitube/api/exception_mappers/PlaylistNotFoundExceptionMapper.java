package nl.han.oose.dea.spotitube.api.exception_mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import nl.han.oose.dea.spotitube.business.exceptions.PlaylistNotFoundException;

@Provider
public class PlaylistNotFoundExceptionMapper implements ExceptionMapper<PlaylistNotFoundException> {
  @Override
  public Response toResponse(PlaylistNotFoundException e) {
    return Response.status(PlaylistNotFoundException.STATUSCODE).entity(e.getMessage()).build();
  }

}
