package com.learningcurve.digitalwolfeassignement.presentataion.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.learningcurvemvvmrecipeapp.presentation.components.GenericDialog
import com.example.learningcurvemvvmrecipeapp.presentation.components.GenericDialogInfo
import com.learningcurve.digitalwolfeassignement.presentataion.components.CircularIndeterminateProgressBar
import java.util.*

private val PinkThemeLight = lightColors(
    primary = pink500,
    onPrimary = Color.Black,

    secondary = pinkDarkPrimary,
    onSecondary = Color.White,

    surface = pink200,
    primaryVariant = pink600,
)

private val PinkThemeDark = darkColors(
    primary = pinkDarkPrimary,
    onPrimary = Color.White,

    secondary = pink500,
    onSecondary = Color.Black,

    surface = pink200,
    primaryVariant = pink600,
)

@Composable
fun MyAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dialogQueue: Queue<GenericDialogInfo>,
    displayProgressBar: Boolean, // loading
    content: @Composable () -> Unit
) {

    val colors = if (darkTheme) {
        PinkThemeDark
    } else {
        PinkThemeLight
    }
    
    MaterialTheme(
        colors = colors,
        typography = QuickSandTypography,
        shapes = Shapes,
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            content()
            CircularIndeterminateProgressBar(
                isDisplayed = displayProgressBar,
                verticalBias = 0.3f
            )
            ProcessDialogQueue(dialogQueue = dialogQueue)
        }
    }
}

@Composable
fun ProcessDialogQueue(
    dialogQueue: Queue<GenericDialogInfo>
){
    dialogQueue.peek()?.let { dialogInfo ->
        GenericDialog(
            onDismiss = dialogInfo.onDismiss,
            title = dialogInfo.title,
            description = dialogInfo.description,
            positiveAction = dialogInfo.positiveAction,
            negativeAction = dialogInfo.negativeAction
        )
    }
}
