package server.askboard.group.myserveraskboard.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import server.askboard.group.myserveraskboard.entities.User;
import server.askboard.group.myserveraskboard.services.UserServiceImpl;

@Component
public class UserValidator implements Validator {
    @Autowired
    private UserServiceImpl userService;
    
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }
    
    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username",
                                                  "Username needs to be between 6 and 32 " + "characters!");
        if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "Username needs to be between 6 and 32 " + "characters!");
        }
        if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "User already exists!");
        }
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
                                                  "Password needs to be between 8 and 32 " + "characters!");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Password needs to be between 8 and 32 " + "characters!");
        }
    }
}