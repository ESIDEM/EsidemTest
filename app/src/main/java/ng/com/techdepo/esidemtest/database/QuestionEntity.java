package ng.com.techdepo.esidemtest.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import ng.com.techdepo.esidemtest.models.Options;

@Entity(tableName = "questions")
public class QuestionEntity {

    int id;
    @PrimaryKey @NonNull
    int questionId;
    String question;
    Options options;
    String section;
    String image;
    String answer;
    String solution;
    String examtype;
    String examyear;

    public QuestionEntity(int questionId, String question, Options options, String answer, String examtype, String examyear) {
        this.questionId = questionId;
        this.question = question;
        this.options = options;
        this.answer = answer;
        this.examtype = examtype;
        this.examyear = examyear;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getExamtype() {
        return examtype;
    }

    public void setExamtype(String examtype) {
        this.examtype = examtype;
    }

    public String getExamyear() {
        return examyear;
    }

    public void setExamyear(String examyear) {
        this.examyear = examyear;
    }
}
