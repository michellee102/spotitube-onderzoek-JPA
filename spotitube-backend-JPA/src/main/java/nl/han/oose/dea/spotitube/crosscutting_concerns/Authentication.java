package nl.han.oose.dea.spotitube.crosscutting_concerns;

import at.favre.lib.crypto.bcrypt.BCrypt;
import nl.han.oose.dea.spotitube.business.exceptions.UnauthorizedException;


public class Authentication {

  // Hash password functies
  public String hashPassword(String password){
    return BCrypt.withDefaults().hashToString(12, password.toCharArray());
  }

  public void checkPassword(String password, String hashedPassword) {
    if (!BCrypt.verifyer().verify(password.toCharArray(), hashedPassword).verified) {
      throw new UnauthorizedException("The password you entered is incorrect.");
    }
  }


}
