package com.sublimetech.supervisor.presentation.ui.profesional.woman.homeScreen.components

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import com.sublimetech.supervisor.data.model.locations.TownDto


@OptIn(ExperimentalFoundationApi::class)
@ExperimentalMaterialApi
@Composable
fun TownList(
    townsDtoList: List<TownDto>,
    onTownClick: (Array<String>) -> Unit
) {
    val orientation = LocalConfiguration.current.orientation
    val state = rememberLazyGridState()


    LazyVerticalGrid(
        columns = GridCells.Fixed(
            when (orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> {
                    4
                }

                else -> {
                    2
                }
            }
        ),
        state = state
    ) {
        items(townsDtoList) {
            TownsDtoItems(it, onClick = { onTownClick(arrayOf(it.projectId, it.id)) })
        }
    }

}