package ng.com.techdepo.esidemtest.utils

import android.app.Application



import ng.com.techdepo.esidemtest.di.components.AppComponent
import ng.com.techdepo.esidemtest.di.components.DaggerAppComponent
import ng.com.techdepo.esidemtest.di.module.AppModule
import ng.com.techdepo.esidemtest.di.module.NetWorkModule


class QuestionsApplication : Application(){

    lateinit var appComponent:AppComponent

    override fun onCreate() {
        super.onCreate()

       appComponent = DaggerAppComponent.builder()
               .appModule(AppModule(this)).netWorkModule(NetWorkModule()).build()
    }

    fun appComponent():AppComponent{
        return appComponent
    }
}
