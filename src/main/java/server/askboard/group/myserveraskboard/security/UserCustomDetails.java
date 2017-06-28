package server.askboard.group.myserveraskboard.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import server.askboard.group.myserveraskboard.entities.Role;
import server.askboard.group.myserveraskboard.entities.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Brauni on 28.06.2017.
 */
public class UserCustomDetails implements UserDetails {

    private String username;
    private String password;
    Collection<? extends GrantedAuthority> authorities;

    public UserCustomDetails(User byUsername) {
        this.username = byUsername.getUsername();
        this.password  = byUsername.getPassword();

        List<GrantedAuthority> auths = new ArrayList<>();
        for(Role role: byUsername.getRoles()){
            auths.add(new SimpleGrantedAuthority(role.getName().toUpperCase()));
        }

        this.authorities = auths;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
