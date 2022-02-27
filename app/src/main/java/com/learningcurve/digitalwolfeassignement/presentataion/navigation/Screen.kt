package com.learningcurve.digitalwolfeassignement.presentataion.navigation

sealed class Screen(
    val route: String
) {
    object HOME_SCREEN_ROUTE: Screen("home_screen")
    object EDIT_SCREEN_ROUTE: Screen("edit_screen")

    fun withArgs(vararg args: String): String{
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
