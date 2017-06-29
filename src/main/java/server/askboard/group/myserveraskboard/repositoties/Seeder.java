package server.askboard.group.myserveraskboard.repositoties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import server.askboard.group.myserveraskboard.entities.Question;
import server.askboard.group.myserveraskboard.entities.Role;
import server.askboard.group.myserveraskboard.entities.User;

import java.util.Arrays;

@Component
public class Seeder implements CommandLineRunner {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public Seeder(QuestionRepository questionRepository, UserRepository userRepository) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        userRepository.save(new User("admin", "admin", Arrays.asList(new Role("ROLE_ADMIN"), new Role("ROLE_USER"))));
        userRepository.save(new User("Olaf123", "olaf123", Arrays.asList(new Role("ROLE_USER"))));
        userRepository.save(new User("Sepp123", "sepp123", Arrays.asList(new Role("ROLE_USER"))));
        Question olafQuestion = new Question("Olaf123", "First Post",
                                             "Just wanted to create the first post! And then there's some random text.."
                                             + ".; Just for the sake of testing the max displayable amount of character"
                                             + "s in one line. Then teher wasn't anything more that come to mind for "
                                             + "filling any more space!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        questionRepository.save(olafQuestion);
        userRepository.findByUsername(olafQuestion.getOwner()).getQuestions().add(olafQuestion);

        Question seppQuestion = new Question("Sepp123", "Second Post", "Just wanted to create the "
                                                                       + "second post!");

        questionRepository.save(seppQuestion);
        userRepository.findByUsername(seppQuestion.getOwner()).getQuestions().add(seppQuestion);


    }
}
