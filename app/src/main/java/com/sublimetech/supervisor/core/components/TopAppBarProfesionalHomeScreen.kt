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
import androidx.compose.ui.unit.dp

@Composable
fun TopAppBarProfesionalHomeScreen(
    actionIcon: ImageVector?,
    onClickAction: () -> Unit,
    showMenu: Boolean?,
    onClickMenu: () -> Unit,
    menuString: String
) {

    TopAppBar(
        navigationIcon = {},
        title = {},
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
                    DropdownMenuItem(onClick = { onClickMenu() }) {
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