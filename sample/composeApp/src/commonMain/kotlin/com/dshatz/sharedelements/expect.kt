package com.dshatz.sharedelements

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter


expect class Avatar {
    @Composable fun getPainter(): Painter
}

@Composable
expect fun BackHandler(enabled: Boolean = true, onBack: () -> Unit)
