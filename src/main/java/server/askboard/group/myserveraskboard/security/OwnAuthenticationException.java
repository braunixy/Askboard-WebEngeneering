package server.askboard.group.myserveraskboard.security;

import org.springframework.security.core.AuthenticationException;


public class OwnAuthenticationException extends AuthenticationException {
    
    public OwnAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }
    
    public OwnAuthenticationException(String msg) {
        super(msg);
    }
}
