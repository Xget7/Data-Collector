package com.sublimetech.supervisor.presentation.ui.profesional.family.newForm.components

import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarViewMonth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.sublimetech.supervisor.presentation.ui.profesional.woman.newForm.components.getCurrentDateTime
import com.sublimetech.supervisor.presentation.ui.profesional.woman.newForm.components.toString
import com.sublimetech.supervisor.ui.theme.Orange
import java.util.Date

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeHolder: String? = null,
    label: String,
    singleLine: Boolean = true,
) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .padding(horizontal = 25.dp, vertical = 10.dp)
    ) {
        OutlinedTextField(
            value = value.split(" ").joinToString(" ") { it.capitalize() },
            onValueChange = { onValueChange(it) },
            placeholder = {
                if (placeHolder != null) {
                    Text(text = placeHolder)
                }
            },
            label = { Text(text = label) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedLabelColor = Color.DarkGray,
                unfocusedLabelColor = Color.DarkGray,
                disabledLabelColor = Color.DarkGray
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.moveFocus(FocusDirection.Next)
                }
            ),
            shape = RoundedCornerShape(30),
            singleLine = singleLine,
            modifier = Modifier
                .fillMaxWidth()
        )
    }

}


@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun DateSelector(
    context: Context,
    fechaSeleccionada: (String) -> Unit,
    fecha: String
) {
    val year: Int
    val month: Int
    val day: Int

    val dateActual = getCurrentDateTime()
    val calendar = Calendar.getInstance()
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()


    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            val edad = if (
                dateActual.toString("dd").toInt() - dayOfMonth >= 0 &&
                dateActual.toString("MM").toInt() - month >= 0
            ) {
                dateActual.toString("yyyy").toInt() - year
            } else {
                dateActual.toString("yyyy").toInt() - year - 1
            }
            fechaSeleccionada("$dayOfMonth/${month + 1}/$year       /       Edad: $edad años")
        }, year, month, day
    )

    Card(
        shape = RoundedCornerShape(30),
        elevation = 0.dp,
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(horizontal = 25.dp, vertical = 10.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(30))
    ) {
        Box(
        ) {
            Text(
                text = "Fecha de nacimiento: $fecha",
                color = if (fecha.isNotBlank()) Color.Black else Color.Gray,
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth()
                    .clickable {
                        datePickerDialog.show()
                    }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            ) {
                Spacer(modifier = Modifier.weight(8f))
                IconButton(
                    onClick = {
                        datePickerDialog.show()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarViewMonth,
                        contentDescription = null,
                        tint = Orange,
                        modifier = Modifier
                            .size(25.dp)
                    )
                }
            }
        }
    }
}
