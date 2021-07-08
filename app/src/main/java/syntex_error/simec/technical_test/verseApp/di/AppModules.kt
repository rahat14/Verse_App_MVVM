package syntex_error.simec.technical_test.verseApp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import syntex_error.simec.technical_test.verseApp.ui.fragment.versesList.VerseListRepository
import syntex_error.simec.technical_test.verseApp.network.VerseInterface
import syntex_error.simec.technical_test.verseApp.data.local.VerseDao
import syntex_error.simec.technical_test.verseApp.data.local.room.LocalDatabase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import syntex_error.simec.technical_test.verseApp.utils.Constants
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModules {

    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder()

        okHttpClient.callTimeout(40, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(40, TimeUnit.SECONDS)
        okHttpClient.readTimeout(40, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(40, TimeUnit.SECONDS)
        okHttpClient.addInterceptor(loggingInterceptor)
        okHttpClient.build()
        return okHttpClient.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    }

    @Singleton
    @Provides
    fun provideNewsInterface(retrofit: Retrofit): VerseInterface {
        return retrofit.create(VerseInterface::class.java)
    }


    @Singleton
    @Provides
    fun provideRepository(newsInterface: VerseInterface): VerseListRepository {
        return VerseListRepository(newsInterface)
    }


    @Singleton
    @Provides
    fun provideNewsDatabase(@ApplicationContext context: Context): LocalDatabase {
        return LocalDatabase.getInstance(context)
    }


    @Singleton
    @Provides
    fun provideNewsDao(localDatabase: LocalDatabase): VerseDao {
        return localDatabase.getNewsDao()
    }

}