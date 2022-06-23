package com.example.githubapp.di

import android.content.Context
import com.example.githubapp.data.remote.request.Api
import com.example.githubapp.data.repository.AppRepositoryImpl
import com.example.githubapp.data.repository.TokenSharedPrefRepositoryImpl
import com.example.githubapp.domain.repository.AppRepository
import com.example.githubapp.domain.repository.TokenSharedPrefRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Modules {
    private val json = Json { ignoreUnknownKeys = true }
    private val mediaType = MediaType.get("application/json; charset=utf-8")
    @Singleton
    @Provides
    fun provideGitApi(): Api = Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .addConverterFactory(json.asConverterFactory(mediaType))
        .build()
        .create(Api::class.java)

    @Singleton
    @Provides
    fun provideAppRepository(api: Api): AppRepository =
        AppRepositoryImpl(api)

    @Singleton
    @Provides
    fun provideTokenSharedPrefRepository(@ApplicationContext context: Context) : TokenSharedPrefRepository =
        TokenSharedPrefRepositoryImpl(context)
}