package com.learningcurve.digitalwolfeassignement.presentataion.components

import android.annotation.SuppressLint
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun TypeSpinner(
    onTypeSelected: (String) -> Unit
) {
    val typeList = mutableListOf("Percentage", "Monetary")
    var typeName: String by remember { mutableStateOf(typeList[0]) }
    var expanded by remember { mutableStateOf(false) }
    val transitionState = remember { MutableTransitionState(expanded).apply {
        targetState = !expanded
    }}
    val transition = updateTransition(targetState = transitionState, label = "transition")
    val arrowRotationDegree by transition.animateFloat({
        tween(durationMillis = 300)
    }, label = "rotationDegree") {
        if (expanded) 180f else 0f
    }
    Row(
        modifier = Modifier
            .clickable {
                expanded = !expanded
            }
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = typeName,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(end = 8.dp)
                .height(30.dp)
        )
        Icon(
            imageVector = Icons.Filled.ArrowDropDown,
            contentDescription = "Spinner",
            modifier = Modifier.rotate(arrowRotationDegree)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            typeList.forEach { data ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        typeName = data
                        onTypeSelected(typeName)
                    }
                ) {
                    Text(text = data)
                }
            }
        }
    }
}