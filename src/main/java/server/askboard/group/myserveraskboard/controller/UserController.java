package server.askboard.group.myserveraskboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import server.askboard.group.myserveraskboard.entities.User;
import server.askboard.group.myserveraskboard.security.UserValidator;
import server.askboard.group.myserveraskboard.services.UserService;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@RequestBody User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return "registration";
        }

        userService.save(userForm);

        return "redirect:/questions/all";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and / or password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "index";
    }

    @RequestMapping(value = {"/p", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        return "index";
    }

    @RequestMapping(value = "/" , method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @GetMapping("/a")
    public RedirectView redirectWithUsingRedirectView(RedirectAttributes attributes) {
        return new RedirectView("/index.html");
    }
}