package com.learningcurve.digitalwolfeassignement.interactors

import android.util.Log
import com.learningcurve.digitalwolfeassignement.cache.MyDao
import com.learningcurve.digitalwolfeassignement.cache.mapper.MyEntityMapper
import com.learningcurve.digitalwolfeassignement.domain.data.DataState
import com.learningcurve.digitalwolfeassignement.domain.model.MyModel
import com.learningcurve.digitalwolfeassignement.util.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateEntryInDatabase(
    private val myDao: MyDao,
    private val myEntityMapper: MyEntityMapper,
) {
    fun execute(
        newModel: MyModel,
        oldModel: MyModel
    ): Flow<DataState<MyModel>> = flow {

        try {

            Log.d(TAG, "execute: old = ${oldModel.title}")
            Log.d(TAG, "execute: new = ${newModel.title}")
            emit(DataState.loading())

//            myDao.updateEntity(
//                myEntityMapper.mapFromDomainModel(domainModel)
//            )

            // delete old model
            myDao.deleteEntity(oldModel.title)

            // insert new value
            myDao.insertMyEntity(myEntityMapper.mapFromDomainModel(newModel))


            val cacheResult = myDao.getEntityByTitle(newModel.title)
            Log.d(TAG, "execute: cacheResult = ${cacheResult}")
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