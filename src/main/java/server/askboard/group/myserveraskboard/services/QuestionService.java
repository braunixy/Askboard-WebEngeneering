package server.askboard.group.myserveraskboard.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.askboard.group.myserveraskboard.entities.Answer;
import server.askboard.group.myserveraskboard.entities.Question;
import server.askboard.group.myserveraskboard.repositoties.QuestionRepository;
import server.askboard.group.myserveraskboard.repositoties.UserRepository;

import java.util.Date;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Question> allQuestions(){
        return questionRepository.findAll();
    }

    public void insert(Question question){
        if(question.getCreationDate() == null){
            question.setCreationDate(new Date());
        }
        question.setAnswered(false);
        question.setWithoutAnswers(true);
        questionRepository.save(question);
    }

    public Question findByID(Long id) {
        return questionRepository.findOne(id);
    }

    public String delete(Long id) {
        Question deleteQuestion = questionRepository.findOne(id);
        for(Answer answer: deleteQuestion.getAnswers()){
            userRepository.findByUsername(deleteQuestion.getOwner()).getAnswers().remove(answer);
        }
        questionRepository.delete(id);
        return "Question deleted!";
    }

    public List<Question> findByWithoutAnswers() {
        return questionRepository.findByWithoutAnswersTrue();
    }

    public List<Question> findByNotAnswered(){
        return questionRepository.findByAnsweredFalse();
    }

    public Question answerQuestionById(Long id, Answer answer) {
        Question question = questionRepository.findOne(id);
        question.getAnswers().add(answer);
        question.setWithoutAnswers(false);
        if(answer.getCreationDate() == null){
            answer.setCreationDate(new Date());
        }
        answer.setParentId(question.getId());
        answer.setAccepted(false);
        questionRepository.save(question);
        return question;
    }
}
