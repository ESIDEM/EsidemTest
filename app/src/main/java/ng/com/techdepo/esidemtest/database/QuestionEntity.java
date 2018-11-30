package ng.com.techdepo.esidemtest.database;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;


@Entity(tableName = "questions")
public class QuestionEntity {

    int id;
    @PrimaryKey @NonNull
    int questionId;
    String question;
    @Embedded
    OptionDb optionDb;
    String section;
    String image;
    String answer;
    String solution;
    String examtype;
    String examyear;

    @Ignore
    public QuestionEntity(@NonNull int questionId, String question, OptionDb optionDb, String answer, String examtype, String examyear) {
        this.questionId = questionId;
        this.question = question;
        this.optionDb = optionDb;
        this.answer = answer;
        this.examtype = examtype;
        this.examyear = examyear;

    }

    public QuestionEntity(@NonNull int questionId, String question, OptionDb optionDb, String section, String answer, String examtype, String examyear) {
        this.questionId = questionId;
        this.question = question;
        this.optionDb = optionDb;
        this.section = section;
        this.answer = answer;
        this.examtype = examtype;
        this.examyear = examyear;
    }

    public OptionDb getOptionDb() {
        return optionDb;
    }

    public void setOptionDb(OptionDb optionDb) {
        this.optionDb = optionDb;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(@NonNull int questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
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
