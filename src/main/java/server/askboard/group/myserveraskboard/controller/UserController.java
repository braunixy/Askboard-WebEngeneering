package server.askboard.group.myserveraskboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import server.askboard.group.myserveraskboard.entities.User;
import server.askboard.group.myserveraskboard.security.UserValidator;
import server.askboard.group.myserveraskboard.services.UserServiceImpl;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class UserController {
    
    @Autowired
    private UserServiceImpl userService;
    
    @Autowired
    private UserValidator userValidator;
    
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@RequestBody User user, BindingResult bindingResult,
                               HttpServletResponse response) throws IOException {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            response.setStatus(400);
            response.getWriter().println(bindingResult.getFieldError().getCode());
            return "emptyError";
        }
        
        userService.saveAsUser(user);
        
        return "redirect:/questions/all";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }
}