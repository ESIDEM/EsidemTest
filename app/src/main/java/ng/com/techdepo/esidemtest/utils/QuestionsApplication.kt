package ng.com.techdepo.esidemtest.utils

import android.app.Application



import ng.com.techdepo.esidemtest.di.components.AppComponent
import ng.com.techdepo.esidemtest.di.components.DaggerAppComponent
import ng.com.techdepo.esidemtest.di.module.*


class QuestionsApplication : Application(){

    lateinit var appComponent:AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this)).netWorkModule(NetWorkModule())
                .dataBaseModule(DataBaseModule()).viewModelModule(ViewModelModule()).
                        sharedPrefferenceUtilModule(SharedPrefferenceUtilModule()).build()
        }

    fun appComponent():AppComponent{

       return appComponent
    }
}
