package ng.com.techdepo.esidemtest.di.components

import dagger.Component
import ng.com.techdepo.esidemtest.activities.MainActivity
import ng.com.techdepo.esidemtest.di.module.AppModule
import ng.com.techdepo.esidemtest.di.module.DataBaseModule
import ng.com.techdepo.esidemtest.di.module.NetWorkModule
import ng.com.techdepo.esidemtest.repository.AppRepository
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,NetWorkModule::class,DataBaseModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(appRepository: AppRepository)
}