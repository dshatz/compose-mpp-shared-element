package com.dshatz.sharedelements

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource

actual class Avatar(val resourcePath: String) {
    actual @Composable fun getPainter(): Painter {
        return painterResource(resourcePath)
    }
}

@Composable
actual fun BackHandler(enabled: Boolean, onBack: () -> Unit) {

}

actual val users = listOf(
    User(Avatar("avatar1.svg"), "Adam")
)