package nl.han.oose.dea.spotitube.business.exceptions;

import static java.net.HttpURLConnection.*;

public class UnauthorizedException extends RuntimeException{
  public static int STATUSCODE = HTTP_UNAUTHORIZED;
    public UnauthorizedException(String message) {
      super(message);
    }

}
