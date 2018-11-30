package ng.com.techdepo.esidemtest.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "results")
public class Result {

    @PrimaryKey(autoGenerate = true)
    int id;
    String subject;
    int numberOfQuestions;
    int correctAnswers;
    String time;

   @Ignore
    public Result(String subject, int numberOfQuestions, int correctAnswers, String time) {
        this.subject = subject;
        this.numberOfQuestions = numberOfQuestions;
        this.correctAnswers = correctAnswers;
        this.time = time;
    }

    public Result(int id, String subject, int numberOfQuestions, int correctAnswers, String time) {
        this.id = id;
        this.subject = subject;
        this.numberOfQuestions = numberOfQuestions;
        this.correctAnswers = correctAnswers;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
