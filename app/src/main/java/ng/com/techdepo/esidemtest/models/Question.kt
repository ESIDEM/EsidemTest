package ng.com.techdepo.esidemtest.models

import com.google.gson.annotations.SerializedName

import java.io.Serializable

class Question : Serializable {

    @SerializedName("id")
    var id: Int? = 0
    @SerializedName("question")
    var question: String?=null
    @SerializedName("option")
    var options: Options?=null
    @SerializedName("section")
    var section: String?=null
    @SerializedName("image")
    var image: String?=null
    @SerializedName("answer")
    var answer: String?=null
    @SerializedName("solution")
    var solution: String?=null
    @SerializedName("examtype")
    var examtype: String?=null
    @SerializedName("examyear")
    var examyear: String?=null

    constructor(id: Int, question: String, section: String, options: Options, answer: String, examtype: String, examyear: String) {
        this.id = id
        this.question = question
        this.options = options
        this.answer = answer
        this.examtype = examtype
        this.examyear = examyear
        this.section = section
    }

    constructor()
}
