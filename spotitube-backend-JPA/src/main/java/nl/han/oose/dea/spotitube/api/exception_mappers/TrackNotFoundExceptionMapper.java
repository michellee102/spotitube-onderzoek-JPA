package nl.han.oose.dea.spotitube.api.exception_mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import nl.han.oose.dea.spotitube.business.exceptions.TrackNotFoundException;

@Provider
public class TrackNotFoundExceptionMapper implements ExceptionMapper<TrackNotFoundException> {

  @Override
  public Response toResponse(TrackNotFoundException e) {
    return Response.status(TrackNotFoundException.STATUSCODE).entity(e.getMessage()).build();
  }
}
