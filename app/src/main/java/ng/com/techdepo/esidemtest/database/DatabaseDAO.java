package ng.com.techdepo.esidemtest.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DatabaseDAO {

    @Query("SELECT * FROM questions")
    LiveData<List<QuestionEntity>> getAllQuestions();

    @Query("SELECT * FROM questions")
    List<QuestionEntity> getWidgetQuestions();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void inSertQuestions(QuestionEntity questionEntity);


    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateQuestion(QuestionEntity questionEntity);

    @Delete
    void delete(QuestionEntity questionEntity);

    @Query("DELETE FROM questions")
    public void deleteAllQuestions();

    @Query("SELECT * FROM questions WHERE id = :questionId")
    LiveData<List<QuestionEntity>> getQuestionWithId(int questionId);

    @Query("SELECT * FROM questions WHERE id = :questionId")
    List<QuestionEntity> readQuestionWithId(int questionId);

    @Query("DELETE FROM questions WHERE id = :questionId")
    void deleteById(int questionId);
}
