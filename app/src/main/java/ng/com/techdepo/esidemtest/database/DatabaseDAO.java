package ng.com.techdepo.esidemtest.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DatabaseDAO {

    @Query("SELECT * FROM questions")
    LiveData<List<QuestionEntity>> getAllQuestions();

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
