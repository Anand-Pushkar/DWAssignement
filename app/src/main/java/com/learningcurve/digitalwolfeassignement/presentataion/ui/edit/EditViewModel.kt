package com.learningcurve.digitalwolfeassignement.presentataion.ui.edit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learningcurvemvvmrecipeapp.presentation.ui.util.DialogQueue
import com.learningcurve.digitalwolfeassignement.domain.model.MyModel
import com.learningcurve.digitalwolfeassignement.interactors.GetDataByTitle
import com.learningcurve.digitalwolfeassignement.interactors.InsertEntryIntoDatabase
import com.learningcurve.digitalwolfeassignement.interactors.UpdateEntryInDatabase
import com.learningcurve.digitalwolfeassignement.presentataion.ui.edit.EditScreenEvent.GetDataByTitleEvent
import com.learningcurve.digitalwolfeassignement.presentataion.ui.edit.EditScreenEvent.InsertEntryIntoDatabaseEvent
import com.learningcurve.digitalwolfeassignement.presentataion.ui.edit.EditScreenEvent.UpdateEntryInDatabaseEvent
import com.learningcurve.digitalwolfeassignement.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EditViewModel
@Inject
constructor(
    private val getDataByTitle: GetDataByTitle,
    private val insertEntryIntoDatabase: InsertEntryIntoDatabase,
    private val updateEntryInDatabase: UpdateEntryInDatabase
): ViewModel(

) {
    var loading = mutableStateOf(false)
    val dialogQueue = DialogQueue()
    val model: MutableState<MyModel?> = mutableStateOf(null)
    val topBarTitle: MutableState<String> = mutableStateOf("")
    val dataUpdated: MutableState<Boolean> = mutableStateOf(false)
    val formStateChanged: MutableState<Boolean> = mutableStateOf(false)

    // title
    val title = mutableStateOf("")
    private val initialTitleCursorPosition: MutableState<Int> = mutableStateOf(0)
    private val titleCursorPosition = initialTitleCursorPosition.value.takeIf { it <= title.value.length } ?: title.value.length
    val titleTextFieldValue = mutableStateOf(
        TextFieldValue(
            text = title.value,
            selection = TextRange(titleCursorPosition)
        )
    )
    fun setTitleTextFieldValue(tfv: TextFieldValue) {
        titleTextFieldValue.value = tfv
        title.value = tfv.text
        updateFormStateChanged(true)
    }

    // value
    val value = mutableStateOf("")
    private val initialValueCursorPosition: MutableState<Int> = mutableStateOf(0)
    private val valueCursorPosition = initialValueCursorPosition.value.takeIf { it <= value.value.length } ?: value.value.length
    val valueTextFieldValue = mutableStateOf(
        TextFieldValue(
            text = value.value,
            selection = TextRange(valueCursorPosition)
        )
    )
    fun setValueTextFieldValue(tfv: TextFieldValue) {
        valueTextFieldValue.value = tfv
        value.value = tfv.text
        updateFormStateChanged(true)
    }

    // type
    // true = percentage    false = monetary
    val type: MutableState<Boolean> = mutableStateOf(true)
    fun setType(tp: Boolean) {
        type.value = tp
        updateFormStateChanged(true)
    }

    // start date time
    val startDateTime: MutableState<String> = mutableStateOf("")
    private val initialSDTCursorPosition: MutableState<Int> = mutableStateOf(0)
    private val SDTCursorPosition = initialSDTCursorPosition.value.takeIf { it <= value.value.length } ?: value.value.length
    val SDTTextFieldValue = mutableStateOf(
        TextFieldValue(
            text = startDateTime.value,
            selection = TextRange(SDTCursorPosition)
        )
    )
    fun updateSDTTextFieldValue(tfv: TextFieldValue) {
        SDTTextFieldValue.value = tfv
        startDateTime.value = tfv.text
        updateFormStateChanged(true)
    }

    // end date time
    val endDateTime: MutableState<String> = mutableStateOf("")
    private val initialEDTCursorPosition: MutableState<Int> = mutableStateOf(0)
    private val EDTCursorPosition = initialEDTCursorPosition.value.takeIf { it <= value.value.length } ?: value.value.length
    val EDTTextFieldValue = mutableStateOf(
        TextFieldValue(
            text = endDateTime.value,
            selection = TextRange(EDTCursorPosition)
        )
    )
    fun updateEDTTextFieldValue(tfv: TextFieldValue) {
        EDTTextFieldValue.value = tfv
        endDateTime.value = tfv.text
        updateFormStateChanged(true)
    }


    // unlimited time
    val unlimitedTimeChecked: MutableState<Boolean> = mutableStateOf(false)
    fun updateUnlimitedTimeCheckedState(checkedState: Boolean) {
        unlimitedTimeChecked.value = checkedState
        if(checkedState) {
            updateSDTTextFieldValue(
                TextFieldValue(
                    text = "",
                    selection = TextRange(0)
                )
            )
            updateEDTTextFieldValue(
                TextFieldValue(
                    text = "",
                    selection = TextRange(0)
                )
            )
        }
        updateFormStateChanged(true)
    }

    fun updateFormStateChanged(stateChanged: Boolean) {
        formStateChanged.value = stateChanged
    }


    fun selectDateTime(
        context: Context,
        start: Boolean
    ) {
        var dt = ""
        val currentDateTime = Calendar.getInstance()
        val startYear = currentDateTime.get(Calendar.YEAR)
        val startMonth = currentDateTime.get(Calendar.MONTH)
        val startDay = currentDateTime.get(Calendar.DAY_OF_MONTH)
        val startHour = currentDateTime.get(Calendar.HOUR_OF_DAY)
        val startMinute = currentDateTime.get(Calendar.MINUTE)

        DatePickerDialog(context, { _, year, month, day ->
            TimePickerDialog(context, { _, hour, minute ->
                val pickedDateTime = Calendar.getInstance()
                pickedDateTime.set(year, month, day, hour, minute)
                val monthStr: String
                if ((month + 1).toString().length == 1) {
                    monthStr = "0${month + 1}"
                } else {
                    monthStr = month.toString()
                }
                dt = "$day - $monthStr - $year $hour:$minute"
                if(start) {

                    updateSDTTextFieldValue(
                        TextFieldValue(
                            text = dt,
                            selection = TextRange(dt.length)
                        )
                    )

                } else {
                    updateEDTTextFieldValue(
                        TextFieldValue(
                            text = dt,
                            selection = TextRange(dt.length)
                        )
                    )
                }
            }, startHour, startMinute, false).show()
        }, startYear, startMonth, startDay).show()
    }


    fun onTriggerEvent(event: EditScreenEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is GetDataByTitleEvent -> {
                        if(model.value == null) {
                            getDataByTitle(event.title)
                        }
                    }
                    is InsertEntryIntoDatabaseEvent -> {
                        insertData()
                    }
                    is UpdateEntryInDatabaseEvent -> {
                        Log.d(TAG, "onTriggerEvent: UpdateEntryInDatabaseEvent")
                        updateData()
                    }
                }

            } catch (e: Exception) {
                Log.e(TAG, "onTriggerEvent: Exception: ${e}, ${e.cause}")
            }
        }
    }

    private fun getDataByTitle(
        title: String
    ) {
        getDataByTitle.execute(
            title = title
        ).onEach { dataState ->

            loading.value = dataState.loading

            dataState.data?.let { it ->
                model.value = it
                setTitleTextFieldValue(
                    TextFieldValue(
                        text = it.title,
                        selection = TextRange(it.title.length)
                    )
                )
                setValueTextFieldValue(
                    TextFieldValue(
                        text = it.value,
                        selection = TextRange(it.value.length)
                    )
                )
                setType(it.type)
                updateSDTTextFieldValue(
                    TextFieldValue(
                        text = it.startDateTime,
                        selection = TextRange(it.startDateTime.length)
                    )
                )
                updateEDTTextFieldValue(
                    TextFieldValue(
                        text = it.endDateTime,
                        selection = TextRange(it.endDateTime.length)
                    )
                )
                updateUnlimitedTimeCheckedState(it.unlimitedTime)
                updateFormStateChanged(false)
            }

            dataState.error?.let { error ->
                dialogQueue.appendErrorMessage("Error", error)
            }

        }.launchIn(viewModelScope)
    }


    private fun insertData() {
        val dm = MyModel(
            title = this.title.value,
            type = this.type.value,
            value = this.value.value,
            startDateTime = this.endDateTime.value,
            endDateTime = this.endDateTime.value,
            unlimitedTime = this.unlimitedTimeChecked.value
        )
        insertEntryIntoDatabase.execute(
            domainModel = dm
        ).onEach { dataState ->

            loading.value = dataState.loading

            dataState.data?.let {
                // success
                this.dataUpdated.value = true
            }

            dataState.error?.let { error ->
                dialogQueue.appendErrorMessage("Error", error)
            }

        }.launchIn(viewModelScope)

    }

    private fun updateData() {
        val dm = MyModel(
            title = this.title.value,
            type = this.type.value,
            value = this.value.value,
            startDateTime = this.endDateTime.value,
            endDateTime = this.endDateTime.value,
            unlimitedTime = this.unlimitedTimeChecked.value
        )
        model.value?.let {
            updateEntryInDatabase.execute(
                newModel = dm,
                oldModel = it
            ).onEach { dataState ->

                loading.value = dataState.loading

                dataState.data?.let {
                    // success
                    Log.d(TAG, "updateData: hellllooooooooo")
                    this.dataUpdated.value = true
                }

                dataState.error?.let { error ->
                    dialogQueue.appendErrorMessage("Error", error)
                }

            }.launchIn(viewModelScope)
        }
    }

}