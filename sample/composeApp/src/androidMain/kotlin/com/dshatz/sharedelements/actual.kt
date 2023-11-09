package com.dshatz.sharedelements

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource

actual class Avatar(@DrawableRes val resId: Int) {
    actual @Composable fun getPainter(): Painter = painterResource(resId)
}

actual val users = listOf(
    User(Avatar(R.drawable.avatar_1), "Adam"),
    User(Avatar(R.drawable.avatar_2), "Andrew"),
    User(Avatar(R.drawable.avatar_3), "Anna"),
    User(Avatar(R.drawable.avatar_4), "Boris"),
    User(Avatar(R.drawable.avatar_5), "Carl"),
    User(Avatar(R.drawable.avatar_6), "Donna"),
    User(Avatar(R.drawable.avatar_7), "Emily"),
    User(Avatar(R.drawable.avatar_8), "Fiona"),
    User(Avatar(R.drawable.avatar_9), "Grace"),
    User(Avatar(R.drawable.avatar_10), "Irene"),
    User(Avatar(R.drawable.avatar_11), "Jack"),
    User(Avatar(R.drawable.avatar_12), "Jake"),
    User(Avatar(R.drawable.avatar_13), "Mary"),
    User(Avatar(R.drawable.avatar_14), "Peter"),
    User(Avatar(R.drawable.avatar_15), "Rose"),
    User(Avatar(R.drawable.avatar_16), "Victor")
)

@Composable
actual fun BackHandler(enabled: Boolean, onBack: () -> Unit) {
    androidx.activity.compose.BackHandler(enabled, onBack)
}