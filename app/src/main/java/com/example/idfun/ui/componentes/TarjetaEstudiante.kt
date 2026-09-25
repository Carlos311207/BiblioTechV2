package com.example.idfun.ui.componentes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.idfun.modelo.Estudiante

@Composable
fun TarjetaEstudiante(
    estudiante: Estudiante,
    onVerDetalles: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(10.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Estudiante",
                    modifier = Modifier.size(32.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "${estudiante.nombres} ${estudiante.apellidos}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = "Carnet: ${estudiante.carnet}")
                    Text(text = "${estudiante.grado} - Sección ${estudiante.seccion}")
                }
            }
        }

        Button(
            onClick = onVerDetalles,
            modifier = Modifier
                .align(Alignment.End)
                .padding(6.dp)
        ) {
            Text("Ver")
        }
    }
}