package com.learningcurve.digitalwolfeassignement.presentataion.ui.edit

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarViewMonth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.learningcurve.digitalwolfeassignement.presentataion.components.GenericTopBar
import com.learningcurve.digitalwolfeassignement.presentataion.components.TypeSpinner
import com.learningcurve.digitalwolfeassignement.presentataion.theme.MyAppTheme
import com.learningcurve.digitalwolfeassignement.util.TAG
import java.util.*

@ExperimentalComposeUiApi
@Composable
fun EditScreen(
    viewModel: EditViewModel,
    title: String?,
    onBackPressed: () -> Unit,
) {

    if (title == "null") {
        viewModel.topBarTitle.value = "Add"
    }
    else if (title == "emptyTitle") {
        viewModel.topBarTitle.value = "Edit"
        viewModel.onTriggerEvent(EditScreenEvent.GetDataByTitleEvent(""))
    }
    else {
        viewModel.topBarTitle.value = "Edit"
        title?.let { viewModel.onTriggerEvent(EditScreenEvent.GetDataByTitleEvent(it)) }
    }

    val titleTfv = viewModel.titleTextFieldValue
    val valueTfv = viewModel.valueTextFieldValue
    val unlimitedTimeChecked = viewModel.unlimitedTimeChecked
    val topBarTitle = viewModel.topBarTitle.value
    val dataUpdated = viewModel.dataUpdated.value
    val SDTTextFieldValue = viewModel.SDTTextFieldValue
    val EDTTextFieldValue = viewModel.EDTTextFieldValue
    val formStateChanged = viewModel.formStateChanged.value

    val context = LocalContext.current
    val dialogQueue = viewModel.dialogQueue
    val loading = viewModel.loading.value

    MyAppTheme(
        dialogQueue = dialogQueue.queue.value,
        displayProgressBar = loading
    ) {
        Scaffold(
            topBar = {
                GenericTopBar(
                    title = topBarTitle,
                    dataUpdated = dataUpdated,
                    onSave = {
                        if(title == "null"){
                            viewModel.onTriggerEvent(EditScreenEvent.InsertEntryIntoDatabaseEvent)
                        } else {
                            Log.d(TAG, "EditScreen: UpdateEntryInDatabaseEvent")
                            viewModel.onTriggerEvent(EditScreenEvent.UpdateEntryInDatabaseEvent)
                        }
                    },
                    onBackPressed = onBackPressed,
                    formStateChanged = formStateChanged
                )
            },
            backgroundColor = if(isSystemInDarkTheme()) { MaterialTheme.colors.primary } else { Color.White }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                Title(
                    textFieldValue = titleTfv,
                    onTitleChanged = { viewModel.setTitleTextFieldValue(it) }
                )

                Spacer(modifier = Modifier.height(12.dp))

                TypeAndValue(
                   valueTfv = valueTfv,
                   onTypeSelected = { viewModel.setType(it) },
                   onValueSet = { viewModel.setValueTextFieldValue(it) }
                )

                Spacer(modifier = Modifier.height(12.dp))

                StartDateAndTime(
                    textFieldValue = SDTTextFieldValue,
                    onDateTimeChanged = { viewModel.updateSDTTextFieldValue(it) },
                    onIconClicked = { viewModel.selectDateTime(context, true) },
                    unlimitedTimeChecked = unlimitedTimeChecked.value
                )

                Spacer(modifier = Modifier.height(12.dp))

                EndDateAndTime(
                    textFieldValue = EDTTextFieldValue,
                    onDateTimeChanged = { viewModel.updateEDTTextFieldValue(it) },
                    onIconClicked = { viewModel.selectDateTime(context, false) },
                    unlimitedTimeChecked = unlimitedTimeChecked.value
                )

                Spacer(modifier = Modifier.height(12.dp))

                UnlimitedTime(
                    unlimitedTimeChecked = unlimitedTimeChecked.value,
                    onCheckedChange = { viewModel.updateUnlimitedTimeCheckedState(it) }
                )

            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun Title(
    textFieldValue: MutableState<TextFieldValue>,
    onTitleChanged: (TextFieldValue) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        modifier = Modifier
            .fillMaxWidth(),
        enabled = true,
        value = textFieldValue.value,
        onValueChange = {
            if (it.text != textFieldValue.value.text) {
                onTitleChanged(it)
            }
        },
        label = {
            Text(text = "Title", color = MaterialTheme.colors.onPrimary)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                if (textFieldValue.value.text != "") {

                    // hide the keyboard
                    keyboardController?.hide()

                    onTitleChanged(
                        TextFieldValue().copy(
                            text = textFieldValue.value.text.trim(),
                            selection = TextRange(textFieldValue.value.text.trim().length)
                        )
                    )
                }
            }
        ),
        singleLine = true,
        textStyle = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = if(isSystemInDarkTheme()) { MaterialTheme.colors.primary } else { Color.White },
            focusedIndicatorColor = MaterialTheme.colors.secondary,
            cursorColor = MaterialTheme.colors.onPrimary,
            textColor = MaterialTheme.colors.onPrimary
        ),
    )
}

@ExperimentalComposeUiApi
@Composable
fun Value(
    textFieldValue: MutableState<TextFieldValue>,
    onValueChanged: (TextFieldValue) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        modifier = Modifier,
        enabled = true,
        value = textFieldValue.value,
        onValueChange = {
            if (it.text != textFieldValue.value.text) {
                val number = getValidatedNumber(it.text)
                onValueChanged(
                    TextFieldValue().copy(
                        text = number,
                        selection = TextRange(number.length)
                    )
                )
            }
        },
        label = {
            Text(text = "Value", color = MaterialTheme.colors.onPrimary)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                if (textFieldValue.value.text != "") {

                    // hide the keyboard
                    keyboardController?.hide()

                    val number = getValidatedNumber(textFieldValue.value.text.trim())
                    onValueChanged(
                        TextFieldValue().copy(
                            text = number,
                            selection = TextRange(number.length)
                        )
                    )
                }
            }
        ),
        singleLine = true,
        textStyle = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = if(isSystemInDarkTheme()) { MaterialTheme.colors.primary } else { Color.White },
            focusedIndicatorColor = MaterialTheme.colors.secondary,
            cursorColor = MaterialTheme.colors.onPrimary,
            textColor = MaterialTheme.colors.onPrimary
        ),
    )
}

@ExperimentalComposeUiApi
@Composable
fun StartDateAndTime(
    textFieldValue: MutableState<TextFieldValue>,
    onDateTimeChanged: (TextFieldValue) -> Unit,
    onIconClicked: () -> Unit,
    unlimitedTimeChecked: Boolean
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        modifier = Modifier
            .fillMaxWidth(),
        enabled = !unlimitedTimeChecked,
        value = textFieldValue.value,
        onValueChange = {
            if (it.text != textFieldValue.value.text) {
                onDateTimeChanged(it)
            }
        },
        label = {
            Text(text = "Start Date & Time", color = MaterialTheme.colors.onPrimary)
        },
        trailingIcon = {
            IconButton(
                onClick = {
                    if(!unlimitedTimeChecked){
                        onIconClicked()
                    }
                },
            ) {
                Icon(
                    imageVector = Icons.Filled.CalendarViewMonth,
                    contentDescription = "Clear Search Icon",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                if (textFieldValue.value.text != "") {

                    // hide the keyboard
                    keyboardController?.hide()

                    onDateTimeChanged(
                        TextFieldValue().copy(
                            text = textFieldValue.value.text.trim(),
                            selection = TextRange(textFieldValue.value.text.trim().length)
                        )
                    )
                }
            }
        ),
        singleLine = true,
        textStyle = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = if(isSystemInDarkTheme()) { MaterialTheme.colors.primary } else { Color.White },
            focusedIndicatorColor = MaterialTheme.colors.secondary,
            cursorColor = MaterialTheme.colors.onPrimary,
            textColor = MaterialTheme.colors.onPrimary
        ),
    )
}

@ExperimentalComposeUiApi
@Composable
fun EndDateAndTime(
    textFieldValue: MutableState<TextFieldValue>,
    onDateTimeChanged: (TextFieldValue) -> Unit,
    onIconClicked: () -> Unit,
    unlimitedTimeChecked: Boolean
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        modifier = Modifier
            .fillMaxWidth(),
        enabled = !unlimitedTimeChecked,
        value = textFieldValue.value,
        onValueChange = {
            if (it.text != textFieldValue.value.text) {
                onDateTimeChanged(it)
            }
        },
        label = {
            Text(text = "End Date & Time", color = MaterialTheme.colors.onPrimary)
        },
        trailingIcon = {
            IconButton(
                onClick = {
                    if(!unlimitedTimeChecked) {
                        onIconClicked()
                    }
                },
            ) {
                Icon(
                    imageVector = Icons.Filled.CalendarViewMonth,
                    contentDescription = "Clear Search Icon",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                if (textFieldValue.value.text != "") {

                    // hide the keyboard
                    keyboardController?.hide()

                    onDateTimeChanged(
                        TextFieldValue().copy(
                            text = textFieldValue.value.text.trim(),
                            selection = TextRange(textFieldValue.value.text.trim().length)
                        )
                    )
                }
            }
        ),
        singleLine = true,
        textStyle = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = if (isSystemInDarkTheme()) {
                MaterialTheme.colors.primary
            } else {
                Color.White
            },
            focusedIndicatorColor = MaterialTheme.colors.secondary,
            cursorColor = MaterialTheme.colors.onPrimary,
            textColor = MaterialTheme.colors.onPrimary
        ),
    )
}

@ExperimentalComposeUiApi
@Composable
fun TypeAndValue(
    valueTfv: MutableState<TextFieldValue>,
    onTypeSelected: (Boolean) -> Unit,
    onValueSet: (TextFieldValue) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TypeSpinner(
            onTypeSelected = { type ->
                if (type.lowercase(Locale.getDefault()) == "percentage") {
                    onTypeSelected(true)
                } else {
                    onTypeSelected(false)
                }
            }
        )
        Value(
            textFieldValue = valueTfv,
            onValueChanged = { onValueSet(it) }
        )
    }
}

@Composable
fun UnlimitedTime(
    unlimitedTimeChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .padding(end = 8.dp),
            text = "Unlimited Time"
        )
        Switch(
            checked = unlimitedTimeChecked,
            onCheckedChange = {
                onCheckedChange(it)
            },
        )
    }
}

fun getValidatedNumber(text: String): String {
    // Start by filtering out unwanted characters like commas and multiple decimals
    val filteredChars = text.filterIndexed { index, c ->
        c in "0123456789" ||                      // Take all digits
                (c == '.' && text.indexOf('.') == index)  // Take only the first decimal
    }
    // Now we need to remove extra digits from the input
    return if (filteredChars.contains('.')) {
        val beforeDecimal = filteredChars.substringBefore('.')
        val afterDecimal = filteredChars.substringAfter('.')
        beforeDecimal + "." + afterDecimal.take(2)    // If decimal is present, take first 2 digits after decimal
    } else {
        filteredChars                     // If there is no decimal, just take the original value
    }
}