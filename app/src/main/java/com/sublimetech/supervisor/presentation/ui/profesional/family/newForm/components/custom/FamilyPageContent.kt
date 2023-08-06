package com.sublimetech.supervisor.presentation.ui.profesional.family.newForm.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import com.sublimetech.supervisor.presentation.ui.profesional.family.newForm.PageInfo

@Composable
fun PageContent(
    changePage: (Int) -> Unit,
    page: Int,
    pageNumber: Int,
    numeroDePaginas: Int,
    right: Boolean,
    changeDirection: (Boolean) -> Unit,
    content: @Composable (PageInfo) -> Unit
) {

    AnimatedVisibility(
        visible = page == pageNumber,
        enter = slideInHorizontally(
            initialOffsetX = { if (right) 2 * it else -2 * it },
            animationSpec = tween(durationMillis = 1000)
        ),
        exit = slideOutHorizontally(
            targetOffsetX = { if (!right) it else -it },
            animationSpec = tween(durationMillis = 1000)
        )
    ) {
        content(
            PageInfo(
                page,
                numeroDePaginas,
                { changeDirection(it) },
                { changePage(it) }
            )
        )
    }
}