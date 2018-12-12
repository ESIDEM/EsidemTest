package ng.com.techdepo.esidemtest.di.module

import android.app.Application
import dagger.Module
import javax.inject.Singleton
import dagger.Provides


@Module
class AppModule(val app:Application) {

    @Provides
    @Singleton
    fun providesApplication()=app
}