package com.example.idfun.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.idfun.modelo.Estudiante

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDetalleEstudiante(
    estudiante: Estudiante,
    onRegresar: () -> Unit,
    navController: NavController? = null,
    onEditar: (Int) -> Unit = {},
    onEliminar: (Estudiante) -> Unit = {}
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val backStackEntry = navController?.currentBackStackEntryAsState()?.value
    val mensaje = backStackEntry?.savedStateHandle?.get<String>("mensaje")

    LaunchedEffect(mensaje) {
        if (mensaje != null) {
            snackbarHostState.showSnackbar(mensaje)
            backStackEntry?.savedStateHandle?.remove<String>("mensaje")
        }
    }

    var mostrarDialogo by remember { mutableStateOf(false) }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Estudiante") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Estudiante",
                modifier = Modifier.size(60.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "${estudiante.nombres} ${estudiante.apellidos}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(text = "Carnet: ${estudiante.carnet}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Grado: ${estudiante.grado}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Sección: ${estudiante.seccion}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Estado: ${if (estudiante.activo) "Activo" else "Inactivo"}")

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { onEditar(estudiante.id) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Editar")
                }
                Button(
                    onClick = { mostrarDialogo = true },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Eliminar")
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(
                onClick = onRegresar,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Regresar")
            }

            if (mostrarDialogo) {
                AlertDialog(
                    onDismissRequest = { mostrarDialogo = false },
                    title = { Text("Eliminar estudiante") },
                    text = { Text("¿Estás seguro de que deseas eliminar este estudiante?") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                mostrarDialogo = false
                                onEliminar(estudiante)
                            }
                        ) { Text("Eliminar") }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { mostrarDialogo = false }
                        ) { Text("Cancelar") }
                    }
                )
            }
        }
    }
}
