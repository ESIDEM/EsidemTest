package ng.com.techdepo.esidemtest.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ResultDAO {

    @get:Query("SELECT * FROM results ORDER BY time")
    val allResult: LiveData<List<Result>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inSertResult(result: Result)

    @Query("DELETE FROM results WHERE id = :resultId")
    fun deleteById(resultId: Int)

}
