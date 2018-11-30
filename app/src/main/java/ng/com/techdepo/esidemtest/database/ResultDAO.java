package ng.com.techdepo.esidemtest.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ResultDAO {

    @Query("SELECT * FROM results ORDER BY time")
    LiveData<List<Result>> getAllResult();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void inSertResult(Result result);

    @Query("DELETE FROM results WHERE id = :resultId")
    void deleteById(int resultId);

}
