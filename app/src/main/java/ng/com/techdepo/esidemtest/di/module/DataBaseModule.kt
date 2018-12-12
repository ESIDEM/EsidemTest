package ng.com.techdepo.esidemtest.di.module

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ng.com.techdepo.esidemtest.database.AppDatabase
import ng.com.techdepo.esidemtest.database.DatabaseDAO
import ng.com.techdepo.esidemtest.database.ResultDAO
import javax.inject.Singleton

@Module
class DataBaseModule {

    @Provides
    fun providesAppDatabase(application: Application): AppDatabase {
       return Room.databaseBuilder(
                application, AppDatabase::class.java, "questionlist.db"
        ).build()

    }

    @Singleton
    @Provides
    fun provideQuestionsDAO(database: AppDatabase): DatabaseDAO = database.databaseDAO()

    @Singleton
    @Provides
    fun provideResultDAO(database: AppDatabase): ResultDAO = database.resultDAO()
}