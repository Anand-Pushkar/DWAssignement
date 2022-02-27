package com.learningcurve.digitalwolfeassignement.di

import com.learningcurve.digitalwolfeassignement.cache.MyDao
import com.learningcurve.digitalwolfeassignement.cache.mapper.MyEntityMapper
import com.learningcurve.digitalwolfeassignement.interactors.GetAllData
import com.learningcurve.digitalwolfeassignement.interactors.GetDataByTitle
import com.learningcurve.digitalwolfeassignement.interactors.InsertEntryIntoDatabase
import com.learningcurve.digitalwolfeassignement.interactors.UpdateEntryInDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
object InteractorsModule {

    @ViewModelScoped
    @Provides
    fun provideGetAllData(
        myDao: MyDao,
        myEntityMapper: MyEntityMapper
    ): GetAllData {
        return GetAllData(
            myDao = myDao,
            myEntityMapper = myEntityMapper
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetDataByTitle(
        myDao: MyDao,
        myEntityMapper: MyEntityMapper
    ): GetDataByTitle {
        return GetDataByTitle(
            myDao = myDao,
            myEntityMapper = myEntityMapper
        )
    }

    @ViewModelScoped
    @Provides
    fun provideInsertEntryIntoDatabase(
        myDao: MyDao,
        myEntityMapper: MyEntityMapper
    ): InsertEntryIntoDatabase {
        return InsertEntryIntoDatabase(
            myDao = myDao,
            myEntityMapper = myEntityMapper
        )
    }

    @ViewModelScoped
    @Provides
    fun provideUpdateEntryInDatabase(
        myDao: MyDao,
        myEntityMapper: MyEntityMapper
    ): UpdateEntryInDatabase {
        return UpdateEntryInDatabase(
            myDao = myDao,
            myEntityMapper = myEntityMapper
        )
    }
}