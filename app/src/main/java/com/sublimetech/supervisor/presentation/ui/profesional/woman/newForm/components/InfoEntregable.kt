package com.sublimetech.supervisor.presentation.ui.profesional.woman.newForm.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.sublimetech.supervisor.domain.model.WomanForm.DeliverableTypesList

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InfoEntregable(
    deliverablesTypes: DeliverableTypesList,
    closeDialog: () -> Unit,
    count: Int,
    opcionalValue: String,
    onOpcionalChange: (String) -> Unit,
    onCountChange: (Int) -> Unit,
    continueClick: () -> Unit,
) {

    Dialog(
        onDismissRequest = { /*TODO*/ },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            elevation = 10.dp,
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .height(500.dp)
                .width(650.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(30.dp)
                    .fillMaxSize()
            ) {
                Row(
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .height(330.dp)
                    ) {
                        DropDownMenu(
                            listaDeOpciones = deliverablesTypes.deliverableTypes,
                            opcionSeleccionada = deliverablesTypes.deliverableType.value,
                            onSelectedOption = {
                                deliverablesTypes.onDeliverableTypeSelected(it)
                                deliverablesTypes.deliverable.value = ""
                            },
                            title = "Tipo de entregables"
                        )

                        DropDownMenu(
                            listaDeOpciones = when (deliverablesTypes.deliverableType.value) {
                                "Instrumentos" -> deliverablesTypes.instrumentList
                                "Materiales" -> deliverablesTypes.materialList
                                "Alimentos" -> deliverablesTypes.foodList
                                "Uniformes" -> deliverablesTypes.uniformList
                                "Equipos" -> deliverablesTypes.equipmentList
                                "Herramientas" -> deliverablesTypes.toolList
                                else -> {
                                    listOf("")
                                }
                            },
                            opcionSeleccionada = deliverablesTypes.deliverable.value,
                            onSelectedOption = deliverablesTypes.onDeliverableSelected,
                            title = "Entregables"
                        )

                        Counter(
                            count
                        ) {
                            onCountChange(it)
                        }
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    OutlinedTextField(
                        value = opcionalValue,
                        textStyle = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.LightGray
                        ),
                        label = { Text(text = "Anotaciones (opcional)") },
                        shape = RoundedCornerShape(20.dp),
                        onValueChange = onOpcionalChange,
                        modifier = Modifier
                            .height(330.dp)
                            .width(330.dp)
                    )
                }

                Spacer(modifier = Modifier.padding(20.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    OutlinedButton(
                        onClick = { closeDialog() },
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(
                            text = "Cancelar",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .padding(horizontal = 10.dp, vertical = 10.dp)
                        )
                    }
                    Button(
                        onClick = continueClick,
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(
                            text = "Confirmar",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .padding(horizontal = 10.dp, vertical = 10.dp)
                        )
                    }
                }

            }
        }
    }
}