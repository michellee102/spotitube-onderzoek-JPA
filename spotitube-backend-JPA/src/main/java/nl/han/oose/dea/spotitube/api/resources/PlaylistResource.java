package nl.han.oose.dea.spotitube.api.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.han.oose.dea.spotitube.api.RequestValidator;
import nl.han.oose.dea.spotitube.business.dto.PlaylistsDTO;
import nl.han.oose.dea.spotitube.business.dto.PlaylistDTO;
import nl.han.oose.dea.spotitube.business.dto.TrackDTO;
import nl.han.oose.dea.spotitube.business.dto.TracksDTO;
import nl.han.oose.dea.spotitube.interfaces.services.PlaylistService;

@Path("playlists")
public class PlaylistResource {

  private PlaylistService playlistService;

  @Inject
  public void setPlaylistService(PlaylistService playlistService) {
    this.playlistService = playlistService;
  }


  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response getAllPlaylists(@QueryParam("token") String token) {
    PlaylistsDTO responseDTO = playlistService.getPlaylists(token);
    return Response
      .ok()
      .entity(responseDTO)
      .build();
  }

  @PUT
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response editPlaylistName(@QueryParam("token") String token, @PathParam("id") int id, PlaylistDTO playlistDTO)  {
    RequestValidator.validatePlaylistRequest(playlistDTO);
    PlaylistsDTO updatedPlaylists = playlistService.updatePlaylistName(id, token, playlistDTO);
    return Response
      .status(Response.Status.CREATED)
      .entity(updatedPlaylists)
      .build();
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response addPlaylist(@QueryParam("token") String token, PlaylistDTO playlistDTO) {
    PlaylistsDTO updatedPlaylists = playlistService.addPlaylist(playlistDTO, token);
    return Response
      .status(Response.Status.CREATED)
      .entity(updatedPlaylists)
      .build();
  }

  @DELETE
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response deletePlaylist(@QueryParam("token") String token, @PathParam("id") int id) {
    PlaylistsDTO updatedPlaylists = playlistService.deletePlaylist(id, token);
    return Response
      .ok()
      .entity(updatedPlaylists)
      .build();
  }

  @GET
  @Path("/{id}/tracks")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllTracksFromPlaylist(@QueryParam("token") String token, @PathParam("id") int id) {
    TracksDTO tracks = playlistService.getTracks(id, token);
    return Response
      .ok()
      .entity(tracks)
      .build();
  }

  @POST
  @Path("/{id}/tracks")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response addTrackToPlaylist(@QueryParam("token") String token, TrackDTO track, @PathParam("id") int playlistID) {
    TracksDTO tracks = playlistService.addTrack(track, token, playlistID);
    return Response
      .ok()
      .entity(tracks)
      .build();
  }

  @DELETE
  @Path("/{id}/tracks/{trackId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response deleteTrackFromPlaylist(@QueryParam("token") String token, @PathParam("id") int playlistID, @PathParam("trackId") int trackID) {
    TracksDTO tracks = playlistService.deleteTrack(token, playlistID, trackID);
    return Response
      .ok()
      .entity(tracks)
      .build();
  }
}

