package com.learningcurve.digitalwolfeassignement.interactors

import android.util.Log
import com.learningcurve.digitalwolfeassignement.cache.MyDao
import com.learningcurve.digitalwolfeassignement.cache.mapper.MyEntityMapper
import com.learningcurve.digitalwolfeassignement.domain.data.DataState
import com.learningcurve.digitalwolfeassignement.domain.model.MyModel
import com.learningcurve.digitalwolfeassignement.util.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAllData(
    private val myDao: MyDao,
    private val myEntityMapper: MyEntityMapper,
) {
    fun execute(): Flow<DataState<List<MyModel>>> = flow {
        try {

            emit(DataState.loading())

            val cacheResult = myDao.getAllEntities()

            if(cacheResult.isNotEmpty()) {
                val list = myEntityMapper.toDomainList(cacheResult)
                emit(DataState.success(list))
            }

        } catch (e: Exception){
            Log.e(TAG, "execute: ${e.message}")
            emit(DataState.error<List<MyModel>>(e.message?: "Unknown error"))
        }
    }
}