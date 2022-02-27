package com.learningcurve.digitalwolfeassignement.presentataion.ui.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learningcurvemvvmrecipeapp.presentation.ui.util.DialogQueue
import com.learningcurve.digitalwolfeassignement.domain.model.MyModel
import com.learningcurve.digitalwolfeassignement.interactors.GetAllData
import com.learningcurve.digitalwolfeassignement.presentataion.ui.home.HomeScreenEvent.GetAllDataEvent
import com.learningcurve.digitalwolfeassignement.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val getAllData: GetAllData
): ViewModel() {

    val dataList: MutableState<List<MyModel>> = mutableStateOf(listOf())
    var loading = mutableStateOf(false)
    val dialogQueue = DialogQueue()

    init {
        onTriggerEvent(GetAllDataEvent)
    }

    // start task - the composable has entered the composition
    fun onStart() {
        onTriggerEvent(GetAllDataEvent)
    }

    // cancel task - the composable has left the composition
    fun onStop() {

    }


    fun onTriggerEvent(event: HomeScreenEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is GetAllDataEvent -> {
                        getAllData()
                    }
                }

            } catch (e: Exception) {
                Log.e(TAG, "onTriggerEvent: Exception: ${e}, ${e.cause}")
            }
        }
    }

    private fun getAllData() {
        getAllData.execute().onEach { dataState ->

            loading.value = dataState.loading

            dataState.data?.let { list ->
                dataList.value = list
                list.forEach { model ->
                    Log.d(TAG, "getAllData: title = ${model.title}")
                    Log.d(TAG, "getAllData: value = ${model.value}")
                    Log.d(TAG, "getAllData: value string = ${model.valueString}")
                    Log.d(TAG, "getAllData: title = ${model.title}")

                }
            }

            dataState.error?.let { error ->
                dialogQueue.appendErrorMessage("Error", error)
            }

        }.launchIn(viewModelScope)
    }

}