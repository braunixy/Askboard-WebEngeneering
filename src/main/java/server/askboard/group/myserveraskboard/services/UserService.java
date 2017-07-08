package server.askboard.group.myserveraskboard.services;

import server.askboard.group.myserveraskboard.entities.User;
import server.askboard.group.myserveraskboard.security.CustomUserDetails;

public interface UserService {
    void save(User user);

    User findByUsername(String username);

    void saveAsUser(User user);
    
    CustomUserDetails getDetailsByUsername(String username);
}