package com.sublimetech.supervisor.core.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopAppBarCustom(
    navigationIcon: ImageVector?,
    onClickNavigation: () -> Unit,
    actionIcon: ImageVector?,
    onClickAction: () -> Unit,
    title: String?,
    showMenu: Boolean?,
    onClickMenu1: () -> Unit,
    menuString: String
) {

    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = { onClickNavigation() },
                modifier = Modifier.padding(horizontal = 0.dp)
            ) {
                if (navigationIcon != null) {
                    Icon(
                        imageVector = navigationIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colors.onBackground,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        },
        title = {
            if (title != null) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    fontSize = 25.sp,
                    color = MaterialTheme.colors.onBackground
                )
            }
        },
        actions = {
            IconButton(
                onClick = { onClickAction() },
                modifier = Modifier.padding(horizontal = 0.dp)
            ) {
                if (actionIcon != null) {
                    Icon(
                        imageVector = actionIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colors.onBackground,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            if (showMenu != null) {
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { onClickAction() }
                ) {
                    DropdownMenuItem(onClick = { onClickMenu1() }) {
                        Text(text = menuString)
                    }
                }
            }

        },
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp,
        modifier = Modifier
            .padding(horizontal = 25.dp)
    )
}