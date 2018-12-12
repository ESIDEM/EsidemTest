package ng.com.techdepo.esidemtest.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ng.com.techdepo.esidemtest.utils.Converter


@Database(entities = arrayOf(QuestionEntity::class, Result::class), version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun databaseDAO(): DatabaseDAO
    abstract fun resultDAO(): ResultDAO

}
