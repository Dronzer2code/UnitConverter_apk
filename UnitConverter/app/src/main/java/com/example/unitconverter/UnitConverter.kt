package com.example.unitconverter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import com.example.unitconverter.R 

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UnitConverter() {

    var inputvalue by remember { mutableStateOf("") }
    var outputvalue by remember { mutableStateOf("") }

    var inputunit by remember { mutableStateOf("Meters") }
    var OutputUnit by remember { mutableStateOf("Meters") }
    var isinputexpanded by remember { mutableStateOf(false) }
    var isoutputexpanded by remember { mutableStateOf(false) }
    var inputConversionfactor by remember { mutableStateOf(1.0) }
    var outputConversionfactor by remember { mutableStateOf(1.0) }

    fun convertunit() {
        val input = inputvalue.toDoubleOrNull() ?: 0.0
        val result =
            ((input * inputConversionfactor / outputConversionfactor) * 100).roundToInt() / 100.0
        outputvalue = result.toString()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // 📷 Background Image
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Text(
                text = "Dronzer2Code",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.85f)) 
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Unit Converter",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = inputvalue,
                        onValueChange = {
                            inputvalue = it
                            convertunit()
                        },
                        label = { Text("Enter Value") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        DropdownButton(
                            label = inputunit,
                            expanded = isinputexpanded,
                            onExpandChange = { isinputexpanded = it },
                            onOptionSelected = { unit, factor ->
                                inputunit = unit
                                inputConversionfactor = factor
                                convertunit()
                            }
                        )

                        DropdownButton(
                            label = OutputUnit,
                            expanded = isoutputexpanded,
                            onExpandChange = { isoutputexpanded = it },
                            onOptionSelected = { unit, factor ->
                                OutputUnit = unit
                                outputConversionfactor = factor
                                convertunit()
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.9f))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Converted Result",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "$outputvalue $OutputUnit",
                                style = MaterialTheme.typography.displaySmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun DropdownButton(
    label : String,
    expanded : Boolean,
    onExpandChange : (Boolean) -> Unit,
    onOptionSelected :(String,Double) -> Unit
) {
    Box(){
        Button(
            onClick = {
                onExpandChange(!expanded)
            },
            modifier = Modifier.wrapContentSize()
        ) {
            Text(text = label)
          Icon(
              imageVector = Icons.Default.ArrowDropDown,
              contentDescription = null,
              modifier = Modifier.rotate(if (expanded) 180f else 0f)
          )

        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                onExpandChange(false)
            }
        ) {

            listOf(
                "Centimeters" to 0.01,
                "Meters" to 1.0,
                "Feet" to 0.3048,
                "Milimeters" to 0.001,
                ).forEach { (Unit, factor) ->
                    DropdownMenuItem(
                        text = {
                            Text(text = Unit)
                        },
                        onClick = {
                            onExpandChange(false)
                            onOptionSelected(Unit , factor)
                        }
                    )
            }
        }
    }
}
