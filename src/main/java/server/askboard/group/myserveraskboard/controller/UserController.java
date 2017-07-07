package server.askboard.group.myserveraskboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import server.askboard.group.myserveraskboard.entities.User;
import server.askboard.group.myserveraskboard.security.UserValidator;
import server.askboard.group.myserveraskboard.services.UserService;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@RequestBody User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return bindingResult.getAllErrors().get(0).toString();
        }

        userService.saveAsUser(userForm);

        return "redirect:/questions/all";
    }

    /*@RequestMapping(value = "/logout" ,method = RequestMethod.POST)
    public String logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "Successfully logged out!";
    }*/
    
    @RequestMapping(value = "/" , method = RequestMethod.GET)
    public String index() {
        return "index";
    }
}