package com.sublimetech.supervisor.core.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun EnterAnimation(content: @Composable () -> Unit) {
    AnimatedVisibility(
        visible = true,
        enter = slideInVertically(
            initialOffsetY = { -40 }
        ) + expandHorizontally(
            expandFrom = Alignment.Start
        ) + fadeIn(initialAlpha = 0.3f),
        exit = slideOutHorizontally(

            targetOffsetX = { 40 }
        ) + slideOutHorizontally(

        ) + fadeOut(targetAlpha = -0.3f),
        content = content,
        initiallyVisible = false
    )
}


@Composable
fun SuccessAnimation(
    isVisible: Boolean,
    onAnimationFinish: () -> Unit,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(com.sublimetech.supervisor.R.raw.sucess_upload))
    val progress by animateLottieCompositionAsState(composition)


    LaunchedEffect(isVisible) {
        if (isVisible) {
            delay(2000)
            onAnimationFinish()
        }
    }

    if (isVisible) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            LottieAnimation(
                composition = composition,
                progress = { progress },
            )
        }
    }
}