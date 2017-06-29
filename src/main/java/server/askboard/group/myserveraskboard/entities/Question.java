package server.askboard.group.myserveraskboard.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String owner;
    private String title;

    @Column(name="text",length=1000)
    private String text;
    private Date creationDate;
    private boolean answered;
    private boolean withoutAnswers;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Answer> answers;

    public Question(){

    }

    public Question(String owner, String title, String text){
        this.owner = owner;
        this.title = title;
        this.text = text;
        creationDate = new Date();
        answered = false;
        withoutAnswers = true;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean isWithoutAnswers() {
        return withoutAnswers;
    }

    public void setWithoutAnswers(boolean withoutAnswers) {
        this.withoutAnswers = withoutAnswers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
