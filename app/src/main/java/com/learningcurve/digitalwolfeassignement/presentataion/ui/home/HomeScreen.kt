package com.learningcurve.digitalwolfeassignement.presentataion.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import com.learningcurve.digitalwolfeassignement.presentataion.components.DataList
import com.learningcurve.digitalwolfeassignement.presentataion.navigation.Screen
import com.learningcurve.digitalwolfeassignement.presentataion.theme.MyAppTheme
import com.learningcurve.digitalwolfeassignement.util.TAG

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToEditScreen: (String) -> Unit
) {
    val dataList = viewModel.dataList.value
    val loading = viewModel.loading.value
    val dialogQueue = viewModel.dialogQueue

    Log.d(TAG, "HomeScreen: ============================")

    MyAppTheme(
        dialogQueue = dialogQueue.queue.value,
        displayProgressBar = loading
    ) {
        Scaffold(
            floatingActionButton = {
                //EXTENDED FAB
                ExtendedFloatingActionButton(
                    text = { Text(text = "ADD ENTRY") },
                    onClick = {
                        val route = Screen.EDIT_SCREEN_ROUTE.withArgs("null")
                        onNavigateToEditScreen(route)
                    },
                    icon = { Icon(Icons.Filled.Add, "") }
                )
            }
        ) {
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
            ) {
                DataList(
                    loading = loading,
                    dataList = dataList,
                    onNavigateToEditScreen = onNavigateToEditScreen
                )
            }

        }
    }

    // use this so viewModel can observe lifecycle events of this composable
    DisposableEffect(key1 = viewModel) {
        viewModel.onStart()
        onDispose { viewModel.onStop() }
    }
}