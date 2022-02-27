package com.learningcurve.digitalwolfeassignement.presentataion.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learningcurve.digitalwolfeassignement.R

@Composable
fun GenericTopBar(
    title: String,
    dataUpdated: Boolean,
    onSave: () -> Unit,
    onBackPressed: () -> Unit,
    formStateChanged: Boolean,
) {
    var isBack by remember { mutableStateOf(false) }
    TopAppBar(
        modifier = Modifier
            .background(MaterialTheme.colors.primary),
        title = {
            Text(
                text = title,
                color = MaterialTheme.colors.onPrimary,
                fontSize = 20.sp
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    isBack = true
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "back icon",
                    tint = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.padding(start = 12.dp)
                )
            }
        },
        actions = {
            if(formStateChanged) {
                IconButton(
                    onClick = {
                        onSave()
                    },
                    enabled = formStateChanged
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_save),
                        contentDescription = "edit / save",
                        tint = MaterialTheme.colors.onPrimary,
                    )
                }
            }
        }

    )
    if (isBack) {
        if (!dataUpdated) {
            AppAlertDialog(
                confirmButtonText = stringResource(id = R.string.dialog_confirm_text),
                dismissButtonText = stringResource(id = R.string.dialog_cancel_text),
                message = stringResource(id = R.string.dialog_discard_warning_text),
                onConfirm = {
                    onBackPressed()
                },
                onDismiss = {
                    isBack = false
                }
            )
        } else {
            onBackPressed()
            isBack = false
        }
    }

}