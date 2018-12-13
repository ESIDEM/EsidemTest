package ng.com.techdepo.esidemtest.database

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "results")
class Result {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var subject: String
    var numberOfQuestions: Int = 0
    var correctAnswers: Int = 0
    var time: String

    @Ignore
    constructor(subject: String, numberOfQuestions: Int, correctAnswers: Int, time: String) {
        this.subject = subject
        this.numberOfQuestions = numberOfQuestions
        this.correctAnswers = correctAnswers
        this.time = time
    }

    constructor(id: Int, subject: String, numberOfQuestions: Int, correctAnswers: Int, time: String) {
        this.id = id
        this.subject = subject
        this.numberOfQuestions = numberOfQuestions
        this.correctAnswers = correctAnswers
        this.time = time
    }
}
