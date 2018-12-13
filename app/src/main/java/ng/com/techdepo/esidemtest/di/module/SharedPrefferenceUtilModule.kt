package ng.com.techdepo.esidemtest.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import ng.com.techdepo.esidemtest.utils.SharedPreferenceUtil
import javax.inject.Singleton

@Module
class SharedPrefferenceUtilModule {

    @Provides
    @Singleton
    fun provideSharedPrefferenceUtil(application: Application):SharedPreferenceUtil{
        return SharedPreferenceUtil(application)
    }
}