package server.askboard.group.myserveraskboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import server.askboard.group.myserveraskboard.entities.Answer;
import server.askboard.group.myserveraskboard.entities.Question;
import server.askboard.group.myserveraskboard.security.UserCustomDetails;
import server.askboard.group.myserveraskboard.services.QuestionService;
import server.askboard.group.myserveraskboard.services.UserService;

import java.util.List;

@RestController
@RequestMapping(value = "/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    private String index() {
        return "Doesn't seem to be what you were looking for.";
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Question> getAllQuestions() {
        return questionService.allQuestions();
    }

    @RequestMapping(value = "/all/unanswered", method = RequestMethod.GET)
    public List<Question> getUnansweredQuestions() {
        return questionService.findByNotAnswered();
    }

    @RequestMapping(value = "/all/noanswer", method = RequestMethod.GET)
    public List<Question> getWithoutAnswers() {
        return questionService.findByWithoutAnswers();
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public Question getQuestionByID(@PathVariable("id") Long id) {
        return questionService.findByID(id);
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public Question askQuestion(@RequestBody Question question) {
        UserCustomDetails details =
                (UserCustomDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        question.setOwner(details.getUsername());
        questionService.insert(question);
        userService.findByUsername(details.getUsername()).getQuestions().add(question);
        return question;
    }

    @RequestMapping(value = "/answer/{id}", method = RequestMethod.POST)
    public Question answerQuestion(@PathVariable("id") Long id, @RequestBody Answer answer) {
        UserCustomDetails details =
                (UserCustomDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        answer.setOwner(details.getUsername());
        Question question = questionService.answerQuestionById(id, answer);
        userService.findByUsername(details.getUsername()).getAnswers().add(answer);
        return question;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public String deleteQuestion(@PathVariable("id") Long id) {
        return questionService.delete(id);
    }
}
