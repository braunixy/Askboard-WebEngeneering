package server.askboard.group.myserveraskboard.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import server.askboard.group.myserveraskboard.entities.Role;
import server.askboard.group.myserveraskboard.entities.User;
import server.askboard.group.myserveraskboard.repositoties.UserRepository;
import server.askboard.group.myserveraskboard.security.CustomUserDetails;

import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public void save(User user) {
        user.setPassword(getPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }
    
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    @Override
    public void saveAsUser(User user) {
        user.setRoles(Arrays.asList(new Role("ROLE_USER"), new Role("ROLE_CLIENT")));
        save(user);
    }
    
    public CustomUserDetails getDetailsByUsername(String username) {
        return new CustomUserDetails(findByUsername(username));
    }
}
