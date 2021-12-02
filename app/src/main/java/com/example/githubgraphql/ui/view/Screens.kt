package com.example.githubgraphql.ui.view

sealed class Screens(val route: String) {
    object ProfileScreen : Screens(route = "profile")
}