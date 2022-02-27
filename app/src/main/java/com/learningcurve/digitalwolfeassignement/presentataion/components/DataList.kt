package com.learningcurve.digitalwolfeassignement.presentataion.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.learningcurvemvvmrecipeapp.presentation.components.NothingHere
import com.learningcurve.digitalwolfeassignement.domain.model.MyModel

@Composable
fun DataList(
    loading: Boolean,
    dataList: List<MyModel>,
    onNavigateToEditScreen: (String) -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        if(loading && dataList.isEmpty()) {

        } else if(dataList.isEmpty()) {
            NothingHere()
        } else {
            StaggeredVerticalGrid(
                maxColumnWidth = 220.dp,
                modifier = Modifier.padding(4.dp)
            ) {
                dataList.forEachIndexed { index, data ->
                    DataCard(
                        myModel = data,
                        onNavigateToEditScreen = onNavigateToEditScreen
                    )
                }
            }
        }
    }
}