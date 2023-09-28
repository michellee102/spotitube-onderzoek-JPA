package nl.han.oose.dea.spotitube.api;

import nl.han.oose.dea.spotitube.business.dto.PlaylistDTO;
import nl.han.oose.dea.spotitube.business.exceptions.MissingParametersException;

public class RequestValidator {

  public static void validatePlaylistRequest(PlaylistDTO playlistDTO) {
    if (playlistDTO.getName() == null || playlistDTO.getTracks() == null) {
      throw new MissingParametersException("Niet alle velden van het request object zijn aanwezig.");
    }
  }
}
