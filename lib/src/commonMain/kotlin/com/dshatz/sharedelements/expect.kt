package com.dshatz.sharedelements

expect fun <T : Any?, R : Any?> List<T>.fastMap(transform: (T) -> R): List<R>

expect fun <T : Any?> List<T>.fastForEach(action: (T) -> Unit)

expect class ChoreographerWrapper constructor() {
    internal fun postCallback(elementInfo: SharedElementInfo, callback: () -> Unit)
    internal fun removeCallback(elementInfo: SharedElementInfo)
    fun clear()
}