package ng.com.techdepo.esidemtest.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Options implements Serializable {

    @SerializedName("a")
    String optionA;
    @SerializedName("b")
    String optionB;
    @SerializedName("c")
    String optionC;
    @SerializedName("d")
    String optionD;

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }
}
