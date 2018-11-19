package ng.com.techdepo.esidemtest.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

import ng.com.techdepo.esidemtest.utils.Converter;

@Entity(tableName = "results")
public class Result {

    @PrimaryKey(autoGenerate = true)
    int id;
    String subject;
    int numberOfQuestions;
    int correctAnswers;
    @TypeConverters(Converter.class)
    Date time;

   @Ignore
    public Result(String subject, int numberOfQuestions, int correctAnswers, Date time) {
        this.subject = subject;
        this.numberOfQuestions = numberOfQuestions;
        this.correctAnswers = correctAnswers;
        this.time = time;
    }

    public Result(int id, String subject, int numberOfQuestions, int correctAnswers, Date time) {
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
