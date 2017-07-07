package server.askboard.group.myserveraskboard.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import server.askboard.group.myserveraskboard.entities.Answer;
import server.askboard.group.myserveraskboard.entities.Question;
import server.askboard.group.myserveraskboard.repositoties.QuestionRepository;
import server.askboard.group.myserveraskboard.repositoties.UserRepository;
import server.askboard.group.myserveraskboard.security.CustomUserDetails;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class QuestionService {
    
    @Autowired
    private QuestionRepository questionRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public List<Question> allQuestions() {
        return questionRepository.findAll();
    }
    
    public void insert(Question question) {
        CustomUserDetails details =
                (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        question.setOwner(details.getUsername());
        
        if (question.getCreationDate() == null) {
            question.setCreationDate(new Date());
        }
        question.setAnswered(false);
        question.setWithoutAnswers(true);
        questionRepository.save(question);
        userRepository.findByUsername(details.getUsername()).getQuestions().add(question);
    }
    
    public Question findByID(Long id) {
        return questionRepository.findOne(id);
    }
    
    public String delete(Long id) {
        Question deleteQuestion = questionRepository.findOne(id);
        CustomUserDetails details =
                (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        if (! details.getUsername().equals(deleteQuestion.getOwner())) {
            return "Only allowed for own Questions!";
        }
        
        for (Answer answer : deleteQuestion.getAnswers()) {
            userRepository.findByUsername(answer.getOwner()).getAnswers().remove(answer);
        }
        userRepository.findByUsername(deleteQuestion.getOwner()).getQuestions().remove(deleteQuestion);
        questionRepository.delete(id);
        
        return "Question deleted!";
    }
    
    public void save(Question question) {
        questionRepository.save(question);
    }
    
    public List<Question> findOwnAnswered() {
        List<Question> allQuestions = questionRepository.findAll();
        List<Question> ownAnswered = new ArrayList<Question>();
        CustomUserDetails details = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String user = details.getUsername();
        
        for (Question question : allQuestions) {
            for (Answer answer : question.getAnswers()) {
                if(answer.getOwner().equals(user)){
                    ownAnswered.add(question);
                    continue;
                }
            }
        }
        
        return ownAnswered;
    }
    
    public List<Question> findByWithoutAnswers() {
        return questionRepository.findByWithoutAnswersTrue();
    }
    
    public List<Question> findByNotAnswered() {
        return questionRepository.findByAnsweredFalse();
    }
    
    public Question answerQuestionById(Long id, Answer answer) {
        CustomUserDetails details =
                (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        Question question = questionRepository.findOne(id);
        question.getAnswers().add(answer);
        question.setWithoutAnswers(false);
        
        if (answer.getCreationDate() == null) {
            answer.setCreationDate(new Date());
        }
        answer.setOwner(details.getUsername());
        answer.setParentId(question.getId());
        answer.setAccepted(false);
        
        questionRepository.save(question);
        userRepository.findByUsername(details.getUsername()).getAnswers().add(answer);
        
        return question;
    }
}
