package nl.han.oose.dea.spotitube.api.exception_mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import nl.han.oose.dea.spotitube.business.exceptions.CouldNotAddPlaylistException;

@Provider
public class CouldNotAddPlaylistExceptionMapper implements ExceptionMapper<CouldNotAddPlaylistException> {
  @Override
  public Response toResponse(CouldNotAddPlaylistException e) {
    return Response.status(CouldNotAddPlaylistException.STATUSCODE).entity(e.getMessage()).build();
  }
}
