package ch.devsecops.security.authentificationservice.application.security.exception;

import org.springframework.security.authentication.AuthenticationServiceException;

/**
 * Excpetions pour le login autre que la méthode POST
 */
public class AuthentificationMethodNotSupportedException extends AuthenticationServiceException {


    public AuthentificationMethodNotSupportedException(String message) {
        super(message);
    }
}
