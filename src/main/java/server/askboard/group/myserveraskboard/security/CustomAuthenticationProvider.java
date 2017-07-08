package server.askboard.group.myserveraskboard.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import server.askboard.group.myserveraskboard.services.UserServiceImpl;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    
    private UserServiceImpl userService;
    private PasswordEncoder encoder;
    
    @Autowired
    public CustomAuthenticationProvider(UserServiceImpl userService, PasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        CustomUserDetails details = null;
        try {
            details = userService.getDetailsByUsername(name);
        }
        catch (NullPointerException e) {
            throw new OwnAuthenticationException("No such User exists");
        }
        if (details != null) {
            if (encoder.matches(password, details.getPassword())) {
                Authentication auth = new UsernamePasswordAuthenticationToken(details.getUsername(),
                                                                              details.getPassword(),
                                                                              details.getAuthorities());
                return auth;
            }
            else {
                throw new OwnAuthenticationException("Invalid User-Password Combination");
            }
        }
        else {
            throw new OwnAuthenticationException("No such User exists");
        }
    }
    
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}