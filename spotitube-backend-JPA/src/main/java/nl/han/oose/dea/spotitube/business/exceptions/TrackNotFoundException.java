package nl.han.oose.dea.spotitube.business.exceptions;

import static java.net.HttpURLConnection.HTTP_NOT_FOUND;

public class TrackNotFoundException extends RuntimeException{
  public static int STATUSCODE = HTTP_NOT_FOUND;
  public TrackNotFoundException(String msg) {
    super(msg);
  }
}
