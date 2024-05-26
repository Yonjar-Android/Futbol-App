package com.yonjar.futbolapp.di

import com.yonjar.futbolapp.leagues.data.network.AuthInterceptor
import com.yonjar.futbolapp.leagues.data.network.LeagueService
import com.yonjar.futbolapp.leagues.data.network.TeamService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private val token:String = "P9zHt7ALes21uL7Yo4Il0a2O4Qpt800xwEXDf6tAXgosPYx2u17DiQanDhqs"
    private val baseUrl = "https://api.sportmonks.com/football/v3"


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl("https://api.sportmonks.com/v3/football/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideLeagueService(retrofit: Retrofit): LeagueService {
        return retrofit.create(LeagueService::class.java)
    }

    @Provides
    @Singleton
    fun provideTeamService(retrofit: Retrofit): TeamService {
        return retrofit.create(TeamService::class.java)
    }

    @Provides
    @Singleton
    fun provideInterceptor(authInterceptor: AuthInterceptor):OkHttpClient{
       return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }
}