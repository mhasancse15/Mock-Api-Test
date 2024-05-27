package com.mhasancse15.task.di


import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mhasancse15.task.BuildConfig
import com.mhasancse15.task.common.utils.mock.MockInterceptor
import com.mhasancse15.task.data.source.BookApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    @Provides
    fun provideConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun provideMockInterceptor(@ApplicationContext context: Context): MockInterceptor {
        return MockInterceptor(context)
    }

    @Provides
    @Singleton
    fun provideMockOkHttpClient(mockInterceptor: MockInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(mockInterceptor)
            .build()
    }

//    @Provides
//    @Singleton
//    fun provideOkHttpClient(loggerInterceptor: HttpLoggingInterceptor): OkHttpClient {
//        val timeOut = 30
//        val httpClient = OkHttpClient().newBuilder()
//            .connectTimeout(timeOut.toLong(), TimeUnit.SECONDS)
//            .readTimeout(timeOut.toLong(), TimeUnit.SECONDS)
//            .writeTimeout(timeOut.toLong(), TimeUnit.SECONDS)
//        httpClient.addInterceptor(loggerInterceptor)
//        return httpClient.build()
//    }

    @Provides
    @Singleton
    fun provideLoggerInterceptor(): HttpLoggingInterceptor {
        /*   val interceptor = HttpLoggingInterceptor { message -> Timber.e(message) }
           interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.HEADERS }
           interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
           return interceptor*/
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

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
    @Singleton
    fun provideMovieService(
        retrofit: Retrofit
    ): BookApiService {
        return retrofit.create(BookApiService::class.java)
    }
}