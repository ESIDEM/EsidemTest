package ng.com.techdepo.esidemtest.di.components

import dagger.Component
import ng.com.techdepo.esidemtest.activities.*
import ng.com.techdepo.esidemtest.di.module.*
import ng.com.techdepo.esidemtest.fragments.ClassicTestFragment
import ng.com.techdepo.esidemtest.fragments.QuestionFragment
import ng.com.techdepo.esidemtest.repository.AppRepository
import ng.com.techdepo.esidemtest.utils.SharedPreferenceUtil
import ng.com.techdepo.esidemtest.view_model.QuestionsViewModel
import ng.com.techdepo.esidemtest.widget.AppWidget
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,NetWorkModule::class,DataBaseModule::class,ViewModelModule::class,
SharedPrefferenceUtilModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(appRepository: AppRepository)
    fun inject(questionsViewModel: QuestionsViewModel)
    fun inject(questionActivity: QuestionActivity)
    fun inject(questionFragment: QuestionFragment)
    fun inject(classicTestFragment: ClassicTestFragment)
    fun inject(resultActivity: ResultActivity)
    fun inject(dataActivity: DataActivity)
    fun inject(splashScreen: SplashScreen)
    fun inject(sharedPreferenceUtil: SharedPreferenceUtil)
    fun inject(updateService: AppWidget.UpdateService)
}