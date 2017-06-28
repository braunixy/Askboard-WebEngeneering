package server.askboard.group.myserveraskboard.repositoties;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.askboard.group.myserveraskboard.entities.Question;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByWithoutAnswersTrue();

    List<Question> findByAnsweredFalse();
}
