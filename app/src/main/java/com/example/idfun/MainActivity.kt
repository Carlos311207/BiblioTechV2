package com.example.idfun

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.idfun.ui.Navegacion
import com.example.idfun.ui.PantallaPrincipal
import com.example.idfun.ui.theme.IdfunTheme

import androidx.lifecycle.lifecycleScope
import com.example.idfun.data.librosPrueba
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val app = application as BibliotecaApplication
        val repository = app.libroRepository
        lifecycleScope.launch(Dispatchers.IO) {
            if (repository.obtenerLibros().isEmpty()) {
                librosPrueba.forEach { libro ->
                    repository.insertarLibro(libro)
                }
            }

            val librosGuardados = repository.obtenerLibros()
            println("LIBROS EN ROOM: ${librosGuardados.size}")
            librosGuardados.forEach {
                println("Libro: ${it.id} - ${it.titulo}")
            }

        }


        enableEdgeToEdge()
        setContent {

            IdfunTheme {

                val navController = rememberNavController()

                Navegacion(
                    navController = navController
                )
            }
        }


    }
}