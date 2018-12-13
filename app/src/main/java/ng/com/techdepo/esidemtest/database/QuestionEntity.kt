package ng.com.techdepo.esidemtest.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.annotation.NonNull


@Entity(tableName = "questions")
class QuestionEntity {

    var id: Int = 0
    @PrimaryKey
    @NonNull
    @get:NonNull
    var questionId: Int = 0
    var question: String
    @Embedded
    var optionDb: OptionDb
    var section: String?=null
    var image: String?=null
    var answer: String
    var solution: String?=null
    var examtype: String
    var examyear: String

    @Ignore
    constructor(@NonNull questionId: Int, question: String, optionDb: OptionDb, answer: String, examtype: String, examyear: String) {
        this.questionId = questionId
        this.question = question
        this.optionDb = optionDb
        this.answer = answer
        this.examtype = examtype
        this.examyear = examyear

    }

    constructor(@NonNull questionId: Int, question: String, optionDb: OptionDb, section: String, answer: String, examtype: String, examyear: String) {
        this.questionId = questionId
        this.question = question
        this.optionDb = optionDb
        this.section = section
        this.answer = answer
        this.examtype = examtype
        this.examyear = examyear
    }
}
