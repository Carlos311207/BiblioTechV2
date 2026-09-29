package com.example.idfun.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.idfun.modelo.Estudiante

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaEditarEstudiante(
    estudiante: Estudiante,
    onGuardar: (Estudiante) -> Unit,
    onCancelar: () -> Unit
) {
    var carnet by remember { mutableStateOf(estudiante.carnet) }
    var nombres by remember { mutableStateOf(estudiante.nombres) }
    var apellidos by remember { mutableStateOf(estudiante.apellidos) }
    var activo by remember { mutableStateOf(estudiante.activo) }

    val listaGrados = listOf("1° Bachillerato", "2° Bachillerato", "3° Bachillerato")
    var grado by remember { mutableStateOf(if (listaGrados.contains(estudiante.grado)) estudiante.grado else listaGrados.first()) }
    var expandirGrado by remember { mutableStateOf(false) }

    val listaSecciones = listOf("A", "B", "C")
    var seccion by remember { mutableStateOf(if (listaSecciones.contains(estudiante.seccion)) estudiante.seccion else listaSecciones.first()) }
    var expandirSeccion by remember { mutableStateOf(false) }

    val coloresTextField = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Color.Yellow,
        unfocusedBorderColor = Color.LightGray,
        focusedTextColor = Color.White,
        unfocusedTextColor = Color.White,
        focusedLabelColor = Color.Yellow,
        unfocusedLabelColor = Color.LightGray,
        cursorColor = Color.Yellow
    )

    Scaffold(
        containerColor = Color.Black,
        topBar = {
            TopAppBar(
                title = { Text("Editar Estudiante", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(20.dp)
        ) {
            OutlinedTextField(
                value = carnet,
                onValueChange = { carnet = it },
                label = { Text("Carnet") },
                modifier = Modifier.fillMaxWidth(),
                colors = coloresTextField
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = nombres,
                onValueChange = { nombres = it },
                label = { Text("Nombres") },
                modifier = Modifier.fillMaxWidth(),
                colors = coloresTextField
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = apellidos,
                onValueChange = { apellidos = it },
                label = { Text("Apellidos") },
                modifier = Modifier.fillMaxWidth(),
                colors = coloresTextField
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ---------- Selector de grado ----------
            ExposedDropdownMenuBox(
                expanded = expandirGrado,
                onExpandedChange = { expandirGrado = !expandirGrado }
            ) {
                OutlinedTextField(
                    value = grado,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Grado") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandirGrado)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    colors = coloresTextField
                )

                ExposedDropdownMenu(
                    expanded = expandirGrado,
                    onDismissRequest = { expandirGrado = false }
                ) {
                    listaGrados.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                grado = opcion
                                expandirGrado = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // ---------- Selector de sección ----------
            ExposedDropdownMenuBox(
                expanded = expandirSeccion,
                onExpandedChange = { expandirSeccion = !expandirSeccion }
            ) {
                OutlinedTextField(
                    value = seccion,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Sección") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandirSeccion)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    colors = coloresTextField
                )

                ExposedDropdownMenu(
                    expanded = expandirSeccion,
                    onDismissRequest = { expandirSeccion = false }
                ) {
                    listaSecciones.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                seccion = opcion
                                expandirSeccion = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row {
                Checkbox(
                    checked = activo,
                    onCheckedChange = { activo = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.Yellow,
                        checkmarkColor = Color.Black,
                        uncheckedColor = Color.LightGray
                    )
                )
                Text(
                    text = "Activo",
                    color = Color.White,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val estudianteEditado = estudiante.copy(
                        carnet = carnet,
                        nombres = nombres,
                        apellidos = apellidos,
                        grado = grado,
                        seccion = seccion,
                        activo = activo
                    )
                    onGuardar(estudianteEditado)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Yellow,
                    contentColor = Color.Black
                )
            ) {
                Text("Guardar Cambios")
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(
                onClick = onCancelar,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancelar", color = Color.White)
            }
        }
    }
}
