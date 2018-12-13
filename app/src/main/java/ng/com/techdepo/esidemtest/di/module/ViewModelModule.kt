package ng.com.techdepo.esidemtest.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import ng.com.techdepo.esidemtest.view_model.QuestionsViewModel
import javax.inject.Singleton

@Module
class ViewModelModule {

    @Provides
    @Singleton
    fun provideViewModel(application: Application):QuestionsViewModel{

        return QuestionsViewModel(application)
    }
}