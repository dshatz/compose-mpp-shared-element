# Shared Elements Transition for [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)

Originated from [mxalbert1996/compose-shared-element](https://github.com/mxalbert1996/compose-shared-elements).

Shared elements transition implementation for compose with various customization options.
Currently providing the following transition patterns:
* Standard shared elements transition
* [Material container transform](https://material.io/design/motion/the-motion-system.html#container-transform) (which basically mimics the [`MaterialContainerTransform`](https://developer.android.com/reference/com/google/android/material/transition/MaterialContainerTransform) of MDC-Android library)

![Shared Elements](images/list.gif) ![Material Container Transform](images/cards.gif)

## Download

This library is highly experimental, so for now no artifacts are published. 

If you want to try, download the zip.

If you are interested in Android only, check [mxalbert1996/compose-shared-element](https://github.com/mxalbert1996/compose-shared-elements).

## How to use

1. Define elements with the same `key` and different `screenKey`s using `SharedElement` or `SharedMaterialContainer` composables.
2. Wrap different screens in a single `SharedElementRoot`.

Transition will start when elements with the same key are detected.

## Multiplatform
Currently supported platforms:
* Android
* Desktop JVM.

## Notes

* `SharedElementsTransitionSpec` of the start element will be used.
* Transition is only applied to the shared elements, so you have to define the transition for the rest yourself. See the [demo](demo) for examples. If you want to prevent an element from showing in your self-defined transition, call `prepareTransition(key)`.
* Elements are composed separately in an overlay during transition, so `remember` won't work as expected. If your element is stateful, define the state outside the `SharedElement` or `SharedMaterialContainer` composables.
* If the element is root element (i.e. direct child of `SharedElementRoot`) and is full-screen (e.g. has `Modifier.fillMaxSize()`), specifying `isFullscreen = true` on it can greatly improve performance and allows you to use stateful composables.
