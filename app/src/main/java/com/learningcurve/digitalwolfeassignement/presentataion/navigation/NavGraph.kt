package com.learningcurve.digitalwolfeassignement.presentataion.navigation

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.*
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.learningcurve.digitalwolfeassignement.presentataion.ui.edit.EditScreen
import com.learningcurve.digitalwolfeassignement.presentataion.ui.edit.EditScreenEvent
import com.learningcurve.digitalwolfeassignement.presentataion.ui.edit.EditViewModel
import com.learningcurve.digitalwolfeassignement.presentataion.ui.home.HomeScreen
import com.learningcurve.digitalwolfeassignement.presentataion.ui.home.HomeViewModel
import com.learningcurve.digitalwolfeassignement.util.TAG

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun NavGraph(
    startDestination: String = Screen.HOME_SCREEN_ROUTE.route,
) {
    val navController = rememberAnimatedNavController()
    Log.d(TAG, "NavGraph: wowowowowowowowowow")

    Box {
        AnimatedNavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            home(
                navController = navController,
            )

            edit(
                navController = navController,
            )
        }
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.home(
    navController: NavHostController,
) {
    composable(
        route = Screen.HOME_SCREEN_ROUTE.route
    ) { backStackEntry: NavBackStackEntry ->

        val viewModel = hiltViewModel<HomeViewModel>()
        Log.d(TAG, "home: <><><><>")
        HomeScreen(
            viewModel = viewModel,
            onNavigateToEditScreen = { route ->
                if(backStackEntry.lifecycleIsResumed()){
                    navController.navigate(route)
                }
            },
        )
    }
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
fun NavGraphBuilder.edit(
    navController: NavHostController,
) {
    composable(
        route = Screen.EDIT_SCREEN_ROUTE.route + "/{title}",
        arguments = listOf(navArgument("title"){
            type = NavType.StringType
        })
    ) { backStackEntry: NavBackStackEntry ->

        val title = backStackEntry.arguments?.getString("title")
        val viewModel = hiltViewModel<EditViewModel>()

        EditScreen(
            viewModel = viewModel,
            title = title,
            onBackPressed = {
                navController.popBackStack()
            }
        )

    }
}

/**
 * If the lifecycle is not resumed it means this NavBackStackEntry already processed a nav event.
 *
 * This is used to de-duplicate navigation events.
 */
fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED
