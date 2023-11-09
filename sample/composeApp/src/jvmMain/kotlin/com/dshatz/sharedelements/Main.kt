package com.dshatz.sharedelements

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.dshatz.sharedelements.UserCardsRoot

fun main() {
    application {
        Window(onCloseRequest = {}) {
            UserCardsRoot()
        }
    }
}