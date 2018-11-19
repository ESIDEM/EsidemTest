package ng.com.techdepo.esidemtest.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

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
