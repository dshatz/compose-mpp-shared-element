package com.dshatz.sharedelements

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

private var selectedUser: Int by mutableStateOf(-1)
private var previousSelectedUser: Int = -1

@Composable
fun UserCardsRoot() {
    SharedElementsRoot {
        val user = selectedUser
        val gridState = rememberLazyGridState()

        BackHandler(enabled = user >= 0) {
            changeUser(-1)
        }

        DelayExit(visible = user < 0) {
            UserCardsScreen(gridState)
        }

        DelayExit(visible = user >= 0) {
            val currentUser = remember { users[user] }
            UserCardDetailsScreen(currentUser)
        }
    }
}

@Composable
private fun UserCardsScreen(listState: LazyGridState) {
    LaunchedEffect(listState) {
        val previousIndex = (previousSelectedUser / 2).coerceAtLeast(0)
        if (!listState.layoutInfo.visibleItemsInfo.any { it.index == previousIndex }) {
            listState.scrollToItem(previousIndex)
        }
    }

    val scope = LocalSharedElementsRootScope.current!!
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = listState,
        contentPadding = PaddingValues(4.dp)
    ) {
        itemsIndexed(users) { i, user ->
            Box(modifier = Modifier.padding(4.dp)) {
                SharedMaterialContainer(
                    key = user.name,
                    screenKey = ListScreen,
                    shape = MaterialTheme.shapes.medium,
                    elevation = 2.dp,
                    transitionSpec = MaterialFadeInTransitionSpec
                ) {
                    Column(
                        modifier = Modifier.clickable(enabled = !scope.isRunningTransition) {
                            scope.changeUser(i)
                        }
                    ) {
                        Image(
                            user.avatar.getPainter(),
                            contentDescription = user.name,
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = user.name,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun UserCardDetailsScreen(user: User) {
    val (fraction, setFraction) = remember { mutableStateOf(1f) }
    // Scrim color
    Surface(color = Color.Black.copy(alpha = 0.32f * (1 - fraction))) {
        SharedMaterialContainer(
            key = user.name,
            screenKey = DetailsScreen,
            isFullscreen = true,
            transitionSpec = MaterialFadeOutTransitionSpec,
            onFractionChanged = setFraction
        ) {
            Surface {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val scope = LocalSharedElementsRootScope.current!!
                    Image(
                        user.avatar.getPainter(),
                        contentDescription = user.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(enabled = !scope.isRunningTransition) {
                                scope.changeUser(-1)
                            },
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = user.name,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
    }
}

@Composable
fun UserListRoot() {
    SharedElementsRoot {
        BackHandler(enabled = selectedUser >= 0) {
            changeUser(-1)
        }

        val listState = rememberLazyListState()
        Crossfade(
            targetState = selectedUser,
            animationSpec = tween(durationMillis = TransitionDurationMillis)
        ) { user ->
            when {
                user < 0 -> UserListScreen(listState)
                else -> UserDetailsScreen(users[user])
            }
        }
    }
}

@Composable
private fun UserListScreen(listState: LazyListState) {
    LaunchedEffect(listState) {
        val previousIndex = previousSelectedUser.coerceAtLeast(0)
        if (!listState.layoutInfo.visibleItemsInfo.any { it.index == previousIndex }) {
            listState.scrollToItem(previousIndex)
        }
    }

    val scope = LocalSharedElementsRootScope.current!!
    LazyColumn(state = listState) {
        itemsIndexed(users) { i, user ->
            ListItem(
                modifier = Modifier.clickable(enabled = !scope.isRunningTransition) {
                    scope.changeUser(i)
                },
                leadingContent = {
                    SharedMaterialContainer(
                        key = user.avatar,
                        screenKey = ListScreen,
                        shape = CircleShape,
                        color = Color.Transparent,
                        transitionSpec = FadeOutTransitionSpec
                    ) {
                        Image(
                            user.avatar.getPainter(),
                            contentDescription = user.name,
                            modifier = Modifier.size(48.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                },
                headlineContent = {
                    SharedElement(
                        key = user.name,
                        screenKey = ListScreen,
                        transitionSpec = CrossFadeTransitionSpec
                    ) {
                        Text(text = user.name)
                    }
                }
            )
        }
    }
}

@Composable
private fun UserDetailsScreen(user: User) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SharedMaterialContainer(
            key = user.avatar,
            screenKey = DetailsScreen,
            shape = MaterialTheme.shapes.medium,
            color = Color.Transparent,
            elevation = 10.dp,
            transitionSpec = FadeOutTransitionSpec
        ) {
            val scope = LocalSharedElementsRootScope.current!!
            Image(
                user.avatar.getPainter(),
                contentDescription = user.name,
                modifier = Modifier
                    .size(200.dp)
                    .clickable(enabled = !scope.isRunningTransition) { scope.changeUser(-1) },
                contentScale = ContentScale.Crop
            )
        }
        SharedElement(
            key = user.name,
            screenKey = DetailsScreen,
            transitionSpec = CrossFadeTransitionSpec
        ) {
            Text(text = user.name, style = MaterialTheme.typography.titleLarge)
        }
    }
}

private fun SharedElementsRootScope.changeUser(user: Int) {
    val currentUser = selectedUser
    if (currentUser != user) {
        val targetUser = if (user >= 0) user else currentUser
        if (targetUser >= 0) {
            users[targetUser].let {
                prepareTransition(it.avatar, it.name)
            }
        }
        previousSelectedUser = selectedUser
        selectedUser = user
    }
}

data class User(val avatar: Avatar, val name: String)

expect val users: List<User>

private const val ListScreen = "list"
private const val DetailsScreen = "details"

private const val TransitionDurationMillis = 1000

private val FadeOutTransitionSpec = MaterialContainerTransformSpec(
    durationMillis = TransitionDurationMillis,
    fadeMode = FadeMode.Out
)
private val CrossFadeTransitionSpec = SharedElementsTransitionSpec(
    durationMillis = TransitionDurationMillis,
    fadeMode = FadeMode.Cross,
    fadeProgressThresholds = ProgressThresholds(0.10f, 0.40f)
)
private val MaterialFadeInTransitionSpec = MaterialContainerTransformSpec(
    pathMotionFactory = MaterialArcMotionFactory,
    durationMillis = TransitionDurationMillis,
    fadeMode = FadeMode.In
)
private val MaterialFadeOutTransitionSpec = MaterialContainerTransformSpec(
    pathMotionFactory = MaterialArcMotionFactory,
    durationMillis = TransitionDurationMillis,
    fadeMode = FadeMode.Out
)
