package com.learningcurve.digitalwolfeassignement.di

import androidx.room.Room
import com.learningcurve.digitalwolfeassignement.cache.AppDatabase
import com.learningcurve.digitalwolfeassignement.cache.MyDao
import com.learningcurve.digitalwolfeassignement.cache.mapper.MyEntityMapper
import com.learningcurve.digitalwolfeassignement.presentataion.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideDb(app: BaseApplication): AppDatabase {
        return Room
            .databaseBuilder(app, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .build()
    }

    @Singleton
    @Provides
    fun provideMyDao(app: AppDatabase): MyDao {
        return app.myDao()
    }

    @Singleton
    @Provides
    fun provideMyCacheMapper(): MyEntityMapper {
        return MyEntityMapper()
    }
}