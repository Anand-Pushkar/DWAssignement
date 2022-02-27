package com.learningcurve.digitalwolfeassignement.presentataion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import com.learningcurve.digitalwolfeassignement.presentataion.navigation.NavGraph
import com.learningcurve.digitalwolfeassignement.presentataion.theme.MyAppTheme
import com.learningcurve.digitalwolfeassignement.util.TAG
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavGraph()
        }
    }
}