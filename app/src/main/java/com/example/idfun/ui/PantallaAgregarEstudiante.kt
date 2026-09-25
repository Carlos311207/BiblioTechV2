package com.example.idfun.ui

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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.idfun.modelo.Estudiante
import com.example.idfun.viewmodel.EstudianteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAgregarEstudiante(
    viewModel: EstudianteViewModel,
    onGuardar: () -> Unit,
    onCancelar: () -> Unit
) {
    var carnet by remember { mutableStateOf("") }
    var nombres by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var activo by remember { mutableStateOf(true) }

    val listaGrados = listOf("1° Bachillerato", "2° Bachillerato", "3° Bachillerato")
    var grado by remember { mutableStateOf(listaGrados.first()) }
    var expandirGrado by remember { mutableStateOf(false) }

    val listaSecciones = listOf("A", "B", "C")
    var seccion by remember { mutableStateOf(listaSecciones.first()) }
    var expandirSeccion by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Agregar Estudiante") }) }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            OutlinedTextField(
                value = carnet,
                onValueChange = { carnet = it },
                label = { Text("Carnet") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = nombres,
                onValueChange = { nombres = it },
                label = { Text("Nombres") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = apellidos,
                onValueChange = { apellidos = it },
                label = { Text("Apellidos") },
                modifier = Modifier.fillMaxWidth()
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
                        .menuAnchor()
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
                        .menuAnchor()
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

            Spacer(modifier = Modifier.height(12.dp))

            Row {
                Checkbox(
                    checked = activo,
                    onCheckedChange = { activo = it }
                )
                Text(
                    text = "Activo",
                    modifier = Modifier.padding(top = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    val nuevoEstudiante = Estudiante(
                        carnet = carnet,
                        nombres = nombres,
                        apellidos = apellidos,
                        grado = grado,
                        seccion = seccion,
                        activo = activo
                    )
                    viewModel.insertarEstudiante(nuevoEstudiante)
                    onGuardar()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Estudiante")
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = onCancelar,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancelar")
            }
        }
    }
}