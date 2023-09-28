package nl.han.oose.dea.spotitube.business.exceptions;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;

public class MissingParametersException extends RuntimeException{
  public static int STATUSCODE = HTTP_BAD_REQUEST;
  public MissingParametersException(String message) {
    super(message);
  }
}
