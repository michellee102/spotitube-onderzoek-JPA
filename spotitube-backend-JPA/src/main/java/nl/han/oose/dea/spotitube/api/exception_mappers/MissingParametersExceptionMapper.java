package nl.han.oose.dea.spotitube.api.exception_mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import nl.han.oose.dea.spotitube.business.exceptions.MissingParametersException;

@Provider
public class MissingParametersExceptionMapper implements ExceptionMapper<MissingParametersException> {
  @Override
  public Response toResponse(MissingParametersException e) {
    return Response.status(MissingParametersException.STATUSCODE).entity(e.getMessage()).build();
  }
}
