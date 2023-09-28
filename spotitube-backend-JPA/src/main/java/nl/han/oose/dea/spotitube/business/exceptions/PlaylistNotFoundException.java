package nl.han.oose.dea.spotitube.business.exceptions;

import static java.net.HttpURLConnection.HTTP_NOT_FOUND;

public class PlaylistNotFoundException extends RuntimeException{
  public static int STATUSCODE = HTTP_NOT_FOUND;

  public PlaylistNotFoundException(String message) {
    super(message);
  }
}
