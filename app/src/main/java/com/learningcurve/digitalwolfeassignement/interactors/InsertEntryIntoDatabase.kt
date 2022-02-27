package com.learningcurve.digitalwolfeassignement.interactors

import android.util.Log
import com.learningcurve.digitalwolfeassignement.cache.MyDao
import com.learningcurve.digitalwolfeassignement.cache.mapper.MyEntityMapper
import com.learningcurve.digitalwolfeassignement.domain.data.DataState
import com.learningcurve.digitalwolfeassignement.domain.model.MyModel
import com.learningcurve.digitalwolfeassignement.util.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class InsertEntryIntoDatabase(
    private val myDao: MyDao,
    private val myEntityMapper: MyEntityMapper,
) {
    fun execute(
        domainModel: MyModel
    ): Flow<DataState<MyModel>> = flow {

        try {

            emit(DataState.loading())

            myDao.insertMyEntity(
                myEntityMapper.mapFromDomainModel(domainModel)
            )

            val cacheResult = myDao.getEntityByTitle(domainModel.title)
            cacheResult?.let {
                val model = myEntityMapper.mapToDomainModel(it)
                emit(DataState.success(model))
            }

        } catch (e: Exception){
            Log.e(TAG, "execute: ${e.message}")
            emit(DataState.error<MyModel>(e.message?: "Unknown error"))
        }
    }
}