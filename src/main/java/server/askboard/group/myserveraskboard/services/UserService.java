package server.askboard.group.myserveraskboard.services;

import server.askboard.group.myserveraskboard.entities.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);

    void saveAsUser(User userForm);
}