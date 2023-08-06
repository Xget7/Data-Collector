package com.sublimetech.supervisor.presentation.ui.profesional.woman.homeScreen.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.sublimetech.supervisor.data.model.locations.TownDto


@Composable
fun TownsDtoItems(
    town: TownDto,
    onClick: () -> Unit
) {
    val orientation = LocalConfiguration.current.orientation

    Card(
        elevation = 10.dp,
        backgroundColor = MaterialTheme.colors.surface,
        shape = RoundedCornerShape(50.dp),
        modifier = Modifier
            .padding(
                horizontal = when (orientation) {
                    Configuration.ORIENTATION_LANDSCAPE -> {
                        20.dp
                    }

                    else -> {
                        50.dp
                    }
                },
                vertical = 20.dp

            )
            .clickable { onClick() }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clickable {
                onClick()
            },
        ) {
            key(town.name) {
                Text(
                    text = town.name,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    modifier = Modifier
                        .padding(top = 20.dp)
                )
            }

            Image(
                painter = rememberAsyncImagePainter(model = town.image),
                contentDescription = "",
                modifier = Modifier
                    .width(320.dp)
                    .height(320.dp)
                    .padding(vertical = 7.dp, horizontal = 20.dp),
            )
            Row(modifier = Modifier.padding(bottom = 20.dp)) {
                Text(
                    text = "Progreso: ",
                    color = Color.Gray
                )
                Text(
                    text = "45%",
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
