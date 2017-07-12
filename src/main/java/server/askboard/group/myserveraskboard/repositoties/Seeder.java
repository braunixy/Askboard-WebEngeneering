package server.askboard.group.myserveraskboard.repositoties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import server.askboard.group.myserveraskboard.entities.Question;
import server.askboard.group.myserveraskboard.entities.Role;
import server.askboard.group.myserveraskboard.entities.User;
import server.askboard.group.myserveraskboard.services.QuestionService;
import server.askboard.group.myserveraskboard.services.UserServiceImpl;

import java.util.Arrays;

@Component
public class Seeder implements CommandLineRunner {
    
    private QuestionService questionService;
    private UserServiceImpl userService;
    
    @Autowired
    public Seeder(QuestionService questionService, UserServiceImpl userService) {
        this.questionService = questionService;
        this.userService = userService;
    }
    
    @Override
    public void run(String... strings) throws Exception {
        boolean create = true;
        for (String string : strings) {
            if (string.equalsIgnoreCase("--cc") || string.equalsIgnoreCase("--createcontent")) {
                create = true;
            }
        }
        if (create) {
            userService.save(new User("admin", "admin", Arrays.asList(new Role("ROLE_ADMIN"), new Role("ROLE_USER"),
                                                                      new Role("ROLE_CLIENT"))));
            userService.save(
                    new User("Olaf123", "olaf123", Arrays.asList(new Role("ROLE_USER"), new Role("ROLE_CLIENT"))));
            userService.save(
                    new User("Sepp123", "sepp123", Arrays.asList(new Role("ROLE_USER"), new Role("ROLE_CLIENT"))));
            userService.save(
                    new User("Adolf123", "adolf123", Arrays.asList(new Role("ROLE_USER"), new Role("ROLE_CLIENT")
                    )));
            
            Question olafQuestion = new Question("Olaf123", "First Post",
                                                 "Just wanted to create the first post! And then there's some random "
                                                 + "text.."
                                                 + ".; Just for the sake of testing the max displayable amount of "
                                                 + "character"
                                                 + "s in one line. Then there wasn't anything more that come to mind "
                                                 + "for "
                                                 + "filling any more "
                                                 + "space!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            
            questionService.save(olafQuestion);
            
            Question seppQuestion = new Question("Sepp123", "Second Post",
                                                 "Just wanted to create the " + "second post!");
            
            questionService.save(seppQuestion);
        }
    }
}
