package com.mhasancse15.task.di


import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mhasancse15.task.BuildConfig
import com.mhasancse15.task.common.utils.MockResponseInterceptor
import com.mhasancse15.task.data.source.BookApiService
import com.mhasancse15.task.presentation.MainActivity
import com.mhasancse15.task.presentation.book.BookListFragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    fun provideRetrofit(
        baseUrl: String,
        okHttpClient: OkHttpClient,
        factory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(factory)
            .build()
    }

    @Provides
    fun provideMockResponseInterceptor(
        @ApplicationContext context: Context
    ): MockResponseInterceptor {
        return MockResponseInterceptor.Builder(context.assets)
            .isGlobalMockingEnabled { BookListFragment.isGlobalMockingEnabled }
            .build()
    }

    @Provides
    fun provideConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun provideLoggerInterceptor(): HttpLoggingInterceptor {
       /* val interceptor = HttpLoggingInterceptor { message -> Timber.e(message) }
        interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.HEADERS }
        interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
        return interceptor*/
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggerInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val timeOut = 30
        val httpClient = OkHttpClient().newBuilder()
            .connectTimeout(timeOut.toLong(), TimeUnit.SECONDS)
            .readTimeout(timeOut.toLong(), TimeUnit.SECONDS)
            .writeTimeout(timeOut.toLong(), TimeUnit.SECONDS)
        httpClient.addInterceptor(loggerInterceptor)
        return httpClient.build()
    }

    @Provides
    @Singleton
    fun provideMovieService(
        retrofit: Retrofit
    ): BookApiService {
        return retrofit.create(BookApiService::class.java)
    }
}