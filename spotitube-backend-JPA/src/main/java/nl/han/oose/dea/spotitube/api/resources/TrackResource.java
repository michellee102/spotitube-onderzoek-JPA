package nl.han.oose.dea.spotitube.api.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.han.oose.dea.spotitube.business.dto.TracksDTO;
import nl.han.oose.dea.spotitube.interfaces.services.TrackService;

@Path("/tracks")
public class TrackResource {

  TrackService trackService;

  @Inject
  public void setTrackService(TrackService trackService) {
    this.trackService = trackService;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAvailableTracks(@QueryParam("token") String token, @QueryParam("forPlaylist") int forPlaylistId){
    TracksDTO availableTracks = trackService.getAvailableTracks(token, forPlaylistId);
    return Response
      .ok()
      .entity(availableTracks)
      .build();
  }


}
