package com.dshatz.sharedelements

import android.view.Choreographer
import androidx.compose.ui.util.fastMap as fastMapAndroid
import androidx.compose.ui.util.fastForEach as fastForEachAndroid

actual fun <T : Any?, R : Any?> List<T>.fastMap(transform: (T) -> R): List<R> {
    return fastMapAndroid(transform)
}


actual fun <T : Any?> List<T>.fastForEach(action: (T) -> Unit) {
    fastForEachAndroid(action)
}


actual class ChoreographerWrapper actual constructor() {
    private val callbacks = mutableMapOf<SharedElementInfo, Choreographer.FrameCallback>()
    private val choreographer = Choreographer.getInstance()

    internal actual fun postCallback(elementInfo: SharedElementInfo, callback: () -> Unit) {
        if (callbacks.containsKey(elementInfo)) return

        val frameCallback = Choreographer.FrameCallback {
            callbacks.remove(elementInfo)
            callback()
        }
        callbacks[elementInfo] = frameCallback
        choreographer.postFrameCallback(frameCallback)
    }

    internal actual fun removeCallback(elementInfo: SharedElementInfo) {
        callbacks.remove(elementInfo)?.also(choreographer::removeFrameCallback)
    }

    actual fun clear() {
        callbacks.values.forEach(choreographer::removeFrameCallback)
        callbacks.clear()
    }
}