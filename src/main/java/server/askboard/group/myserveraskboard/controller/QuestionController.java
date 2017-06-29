package server.askboard.group.myserveraskboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import server.askboard.group.myserveraskboard.entities.Answer;
import server.askboard.group.myserveraskboard.entities.Question;
import server.askboard.group.myserveraskboard.services.QuestionService;

import java.util.List;

@RestController
@RequestMapping(value = "/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

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
        questionService.insert(question);
        return question;
    }

    @RequestMapping(value = "/answer/{id}", method = RequestMethod.POST)
    public Question answerQuestion(@PathVariable("id") Long id, @RequestBody Answer answer) {
        return questionService.answerQuestionById(id, answer);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public String deleteQuestion(@PathVariable("id") Long id) {
        return questionService.delete(id);
    }
}
