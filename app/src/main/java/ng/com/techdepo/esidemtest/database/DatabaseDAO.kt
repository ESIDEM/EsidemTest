package ng.com.techdepo.esidemtest.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.reactivex.Flowable



@Dao
interface DatabaseDAO {

    @get:Query("SELECT * FROM questions")
    val allQuestions: LiveData<List<QuestionEntity>>

    @get:Query("SELECT * FROM questions")
    val widgetQuestions: Flowable<List<QuestionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inSertQuestions(questionEntity: QuestionEntity)


    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateQuestion(questionEntity: QuestionEntity)

    @Delete
    fun delete(questionEntity: QuestionEntity)

    @Query("DELETE FROM questions")
    fun deleteAllQuestions()

    @Query("SELECT * FROM questions WHERE id = :questionId")
    fun getQuestionWithId(questionId: Int): LiveData<List<QuestionEntity>>

    @Query("SELECT * FROM questions WHERE id = :questionId")
    fun readQuestionWithId(questionId: Int): List<QuestionEntity>

    @Query("DELETE FROM questions WHERE id = :questionId")
    fun deleteById(questionId: Int)
}
