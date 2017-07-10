package server.askboard.group.myserveraskboard.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import server.askboard.group.myserveraskboard.entities.Answer;
import server.askboard.group.myserveraskboard.entities.Question;
import server.askboard.group.myserveraskboard.repositoties.QuestionRepository;
import server.askboard.group.myserveraskboard.repositoties.UserRepository;

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
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        
        question.setOwner(username);
        
        if (question.getCreationDate() == null) {
            question.setCreationDate(new Date());
        }
        question.setAnswered(false);
        question.setWithoutAnswers(true);
        questionRepository.save(question);
        userRepository.findByUsername(username).getQuestions().add(question);
    }
    
    public Question findByID(Long id) {
        return questionRepository.findOne(id);
    }
    
    public String delete(Long id) {
        Question deleteQuestion = questionRepository.findOne(id);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        
        if (! username.equals(deleteQuestion.getOwner())) {
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
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        
        for (Question question : allQuestions) {
            for (Answer answer : question.getAnswers()) {
                if (answer.getOwner().equals(username)) {
                    if(ownAnswered.contains(question)){
                        continue;
                    }
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
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        
        Question question = questionRepository.findOne(id);
        question.getAnswers().add(answer);
        question.setWithoutAnswers(false);
        
        if (answer.getCreationDate() == null) {
            answer.setCreationDate(new Date());
        }
        answer.setOwner(username);
        answer.setParentId(question.getId());
        answer.setAccepted(false);
        
        questionRepository.save(question);
        userRepository.findByUsername(username).getAnswers().add(answer);
        
        return question;
    }
    
    public Question answerQuestion(Long idQuestion, Long idAnswer) {
        Question question = questionRepository.findOne(idQuestion);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        
        if (! username.equals(question.getOwner())) {
            return null;
        }
        if(idAnswer > question.getAnswers().size()){
            return null;
        }
        
        question.getAnswers().get((int) (idAnswer - 1)).setAccepted(true);
        question.setAnswered(true);
        question.setAcceptedId(idAnswer);
        
        return question;
    }
}
