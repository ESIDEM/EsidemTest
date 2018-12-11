package ng.com.techdepo.esidemtest.models

import com.google.gson.annotations.SerializedName

class QuestionResponse {

    @SerializedName("data")
    var data: List<Question>? = null
    @SerializedName("status")
    var status: String? = null
    @SerializedName("subject")
    var subject: String? = null
}
