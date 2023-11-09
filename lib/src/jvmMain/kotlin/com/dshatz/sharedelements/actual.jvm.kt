package com.dshatz.sharedelements

actual fun <T : Any?, R : Any?> List<T>.fastMap(transform: (T) -> R): List<R> {
    return map(transform)
}


actual fun <T : Any?> List<T>.fastForEach(action: (T) -> Unit) {
    forEach(action)
}

actual class ChoreographerWrapper actual constructor() {
    internal actual fun postCallback(elementInfo: SharedElementInfo, callback: () -> Unit) {

    }

    internal actual fun removeCallback(elementInfo: SharedElementInfo) {

    }

    actual fun clear() {

    }
}