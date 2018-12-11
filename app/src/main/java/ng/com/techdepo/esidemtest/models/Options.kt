package ng.com.techdepo.esidemtest.models

import com.google.gson.annotations.SerializedName

import java.io.Serializable

class Options : Serializable {

    @SerializedName("a")
    var optionA: String?=null
    @SerializedName("b")
    var optionB: String?=null
    @SerializedName("c")
    var optionC: String?=null
    @SerializedName("d")
    var optionD: String?=null

    constructor()

    constructor(optionA: String, optionB: String, optionC: String, optionD: String) {
        this.optionA = optionA
        this.optionB = optionB
        this.optionC = optionC
        this.optionD = optionD
    }
}
