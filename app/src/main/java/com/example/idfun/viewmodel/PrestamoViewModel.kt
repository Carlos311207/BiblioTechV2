package com.example.idfun.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.idfun.BibliotecaApplication
import com.example.idfun.modelo.Estudiante
import com.example.idfun.modelo.Libro
import com.example.idfun.modelo.Prestamo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PrestamoViewModel(application: Application) : AndroidViewModel(application) {

    //---TRAEMOS TODOS LOS REPOSITORIOS
    private val prestamoRepository =
        (application as BibliotecaApplication).prestamoRepository

    private val libroRepository =
        (application as BibliotecaApplication).libroRepository

    private val estudianteRepository =
        (application as BibliotecaApplication).estudianteRepository

    //LIBROS DISPONIBLES
    private val _librosDisponibles =
        MutableStateFlow<List<Libro>>(emptyList())

    val librosDisponibles = _librosDisponibles

    //ESTUDIANTES ACTIVOS
    private val _estudiantesActivos =
        MutableStateFlow<List<Estudiante>>(emptyList())

    val estudiantesActivos = _estudiantesActivos

    //PRESTAMOS ACTIVOS
    private val _prestamosActivos =
        MutableStateFlow<List<Prestamo>>(emptyList())

    val prestamosActivos = _prestamosActivos

    //----SABER SI EL PRESTAMO YA FUE GUARDADO
    private val _prestamoGuardado =
        MutableStateFlow<Boolean>(false)

    val prestamoGuardado = _prestamoGuardado

    //--------TRAER LOS DATOS AL MOMENTO DE HACER EL REGISTRO
    fun cargarDatos() {
        viewModelScope.launch(context = Dispatchers.IO) {
            //obtenemos todos los libros
            val libros = libroRepository.obtenerLibros()

            //dejamos unicamente los libros disponibles
            _librosDisponibles.value = libros.filter { it.disponible }

            //obtenemos todos los estudiantes
            val estudiantes = estudianteRepository.obtenerEstudiantes()

            //dejamos unicamente los estudiantes activos
            _estudiantesActivos.value = estudiantes.filter { it.activo }

            //obtenemos todos los prestamos activos
            _prestamosActivos.value = prestamoRepository.obtenerPrestamosActivos()
        }
    }

    //----GUARDAR EL PRESTAMO
    fun registrarPrestamo(libroId: Int, estudianteId: Int) {
        viewModelScope.launch(context = Dispatchers.IO) {
            val libro = libroRepository.obtenerLibroPorId(libroId)
            if (libro == null || !libro.disponible) {
                return@launch
            }

            val fechaActual = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
            val prestamo = Prestamo(
                idLibro = libroId,
                idEstudiante = estudianteId,
                fechaPrestamo = fechaActual,
                fechaDevolucion = null,
                devuelto = false,
                fechaLimite = fechaActual
            )

            //Guardar en la base de datos
            prestamoRepository.insertarPrestamo(prestamo)
            //poner el libro en disponible como falso porue se acaba de prestar
            val ibroActualizado = libro.copy(disponible = false)
            libroRepository.actualizarLibro(ibroActualizado)

            //Actualizar los datos de las listas
           val librosActualizados = libroRepository.obtenerLibros()
            _librosDisponibles.value = librosActualizados.filter { it.disponible }
            _prestamosActivos.value = prestamoRepository.obtenerPrestamosActivos()
            _prestamoGuardado.value = true
        }
    }

    //REINICIAR EL ESTADO
    fun reiniciarEstadoGuardado(){
        _prestamoGuardado.value = false
    }
}
