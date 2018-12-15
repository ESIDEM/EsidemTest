package ng.com.techdepo.esidemtest.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import javax.inject.Singleton
import dagger.Provides
import ng.com.techdepo.esidemtest.api.ApiInterface
import ng.com.techdepo.esidemtest.constants.Constants
import ng.com.techdepo.esidemtest.repository.AppRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetWorkModule {

    @Provides
    @Singleton
    fun providesSharedPreferences(application: Application):
    // Application reference must come from AppModule.class
            SharedPreferences {
        return application.getSharedPreferences("ng.com.techdepo.esidemtest", Context.MODE_APPEND)
    }

    @Provides
    @Singleton
    fun provideRetrofit():Retrofit{
        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build()

        return retrofit
    }



    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit):ApiInterface{
        val apiInterface = retrofit.create(ApiInterface::class.java)

        return apiInterface
    }

    @Provides
    @Singleton
    fun provideAppRepo(application: Application):AppRepository{
        return AppRepository(application)
    }
}