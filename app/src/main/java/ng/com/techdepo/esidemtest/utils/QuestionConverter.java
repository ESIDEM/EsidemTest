package ng.com.techdepo.esidemtest.utils;

import ng.com.techdepo.esidemtest.database.OptionDb;
import ng.com.techdepo.esidemtest.database.QuestionEntity;
import ng.com.techdepo.esidemtest.models.Options;
import ng.com.techdepo.esidemtest.models.Question;

public class QuestionConverter {

    public static QuestionEntity convertQuestionToEntity(Question question){

        OptionDb optionDb =new OptionDb(question.getOptions().getOptionA(),
                question.getOptions().getOptionB(), question.getOptions().getOptionC(),
                question.getOptions().getOptionD());

        QuestionEntity questionEntity = new QuestionEntity(question.getId(),
                question.getQuestion(),optionDb,question.getAnswer(),
                question.getExamtype(),question.getExamyear());

        return questionEntity;

    }

    public static Question converEntityToQuestion(QuestionEntity questionEntity){

        Options options = new Options(questionEntity.getOptionDb().getOptionA(),
                questionEntity.getOptionDb().getOptionB(),questionEntity.getOptionDb().getOptionC(),
                questionEntity.getOptionDb().getOptionD());
        Question question = new Question(questionEntity.getId(),questionEntity.getQuestion(),
                options,questionEntity.getAnswer(),
                questionEntity.getExamtype(),questionEntity.getExamyear());

        return question;
    }
}
