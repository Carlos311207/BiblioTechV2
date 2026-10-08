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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class PrestamoViewModel(application: Application) : AndroidViewModel(application) {

    // ---------------------------------------------------------
    // REPOSITORIES
    // ---------------------------------------------------------

    private val prestamoRepository =
        (application as BibliotecaApplication).prestamoRepository

    private val libroRepository =
        (application as BibliotecaApplication).libroRepository

    private val estudianteRepository =
        (application as BibliotecaApplication).estudianteRepository

    // ---------------------------------------------------------
    // LIBROS DISPONIBLES
    // ---------------------------------------------------------

    private val _librosDisponibles =
        MutableStateFlow<List<Libro>>(emptyList())

    val librosDisponibles: StateFlow<List<Libro>> =
        _librosDisponibles.asStateFlow()

    // ---------------------------------------------------------
    // ESTUDIANTES ACTIVOS
    // ---------------------------------------------------------

    private val _estudiantesActivos =
        MutableStateFlow<List<Estudiante>>(emptyList())

    val estudiantesActivos: StateFlow<List<Estudiante>> =
        _estudiantesActivos.asStateFlow()

    // ---------------------------------------------------------
    // PRÉSTAMOS ACTIVOS
    // ---------------------------------------------------------

    private val _prestamos =
        MutableStateFlow<List<Prestamo>>(emptyList())

    val prestamos: StateFlow<List<Prestamo>> =
        _prestamos.asStateFlow()

    val prestamosActivos: StateFlow<List<Prestamo>> =
        _prestamos.asStateFlow()

    // ---------------------------------------------------------
    // INDICA SI EL PRÉSTAMO SE GUARDÓ
    // ---------------------------------------------------------

    private val _prestamoGuardado =
        MutableStateFlow(false)

    val prestamoGuardado: StateFlow<Boolean> =
        _prestamoGuardado.asStateFlow()

    // ---------------------------------------------------------
    // LIBROS RELACIONADOS CON LOS PRÉSTAMOS
    // ---------------------------------------------------------

    private val _librosPrestados =
        MutableStateFlow<Map<Int, Libro>>(emptyMap())

    val librosPrestados: StateFlow<Map<Int, Libro>> =
        _librosPrestados.asStateFlow()

    // ---------------------------------------------------------
    // ESTUDIANTES RELACIONADOS CON LOS PRÉSTAMOS
    // ---------------------------------------------------------

    private val _estudiantesPrestamos =
        MutableStateFlow<Map<Int, Estudiante>>(emptyMap())

    val estudiantesPrestamos: StateFlow<Map<Int, Estudiante>> =
        _estudiantesPrestamos.asStateFlow()

    // ---------------------------------------------------------
    // REGISTRAR PRÉSTAMO
    // ---------------------------------------------------------

    fun registrarPrestamo(
        libroId: Int,
        estudianteId: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {

            // Buscamos el libro seleccionado.
            val libro =
                libroRepository.obtenerLibroPorId(libroId)

            // Verificamos que el libro exista y esté disponible.
            if (libro == null || !libro.disponible) {
                return@launch
            }

            // Obtenemos la fecha actual.
            val fechaActual =
                SimpleDateFormat(
                    "dd/MM/yyyy",
                    Locale.getDefault()
                ).format(Date())

            // Creamos el nuevo préstamo.
            val nuevoPrestamo = Prestamo(
                idLibro = libroId,
                idEstudiante = estudianteId,
                fechaPrestamo = fechaActual,
                fechaDevolucion = null,
                devuelto = false,
                fechaLimite = fechaActual
            )

            // Guardamos el préstamo.
            prestamoRepository.insertarPrestamo(
                nuevoPrestamo
            )

            // El libro deja de estar disponible.
            val libroActualizado =
                libro.copy(
                    disponible = false
                )

            libroRepository.actualizarLibro(
                libroActualizado
            )

            // Actualizamos las listas.
            val librosActualizados =
                libroRepository.obtenerLibros()

            _librosDisponibles.value =
                librosActualizados.filter { it.disponible }

            _prestamos.value =
                prestamoRepository.obtenerPrestamosActivos()

            // Indicamos que el registro terminó correctamente.
            _prestamoGuardado.value = true
        }
    }

    // ---------------------------------------------------------
    // REINICIAR ESTADO DE GUARDADO
    // ---------------------------------------------------------

    fun reiniciarEstadoGuardado() {
        _prestamoGuardado.value = false
    }

    // ---------------------------------------------------------
    // CARGAR DATOS
    // ---------------------------------------------------------

    fun cargarDatos() {
        viewModelScope.launch(Dispatchers.IO) {

            // Obtenemos todos los libros.
            val libros =
                libroRepository.obtenerLibros()

            // Dejamos únicamente los disponibles.
            _librosDisponibles.value =
                libros.filter {
                    it.disponible
                }

            // Obtenemos todos los estudiantes.
            val estudiantes =
                estudianteRepository.obtenerEstudiantes()

            // Dejamos únicamente los activos.
            _estudiantesActivos.value =
                estudiantes.filter {
                    it.activo
                }

            // Obtenemos los préstamos activos.
            val prestamos =
                prestamoRepository.obtenerPrestamosActivos()

            _prestamos.value = prestamos

            // -------------------------------------------------
            // PREPARAMOS LOS LIBROS DE LOS PRÉSTAMOS
            // -------------------------------------------------

            val mapaLibros =
                mutableMapOf<Int, Libro>()

            prestamos.forEach { prestamo ->

                val libro =
                    libroRepository.obtenerLibroPorId(
                        prestamo.idLibro
                    )

                if (libro != null) {
                    mapaLibros[prestamo.idLibro] =
                        libro
                }
            }

            _librosPrestados.value =
                mapaLibros

            // -------------------------------------------------
            // PREPARAMOS LOS ESTUDIANTES DE LOS PRÉSTAMOS
            // -------------------------------------------------

            val mapaEstudiantes =
                mutableMapOf<Int, Estudiante>()

            prestamos.forEach { prestamo ->

                val estudiante =
                    estudianteRepository.obtenerEstudiantePorId(
                        prestamo.idEstudiante
                    )

                if (estudiante != null) {
                    mapaEstudiantes[prestamo.idEstudiante] =
                        estudiante
                }
            }

            _estudiantesPrestamos.value =
                mapaEstudiantes
        }
    }

    // ---------------------------------------------------------
    // DEVOLVER LIBRO
    // ---------------------------------------------------------

    fun devolverPrestamo(
        prestamo: Prestamo
    ) {
        viewModelScope.launch(Dispatchers.IO) {

            // Fecha actual de la devolución.
            val fechaActual =
                SimpleDateFormat(
                    "dd/MM/yyyy",
                    Locale.getDefault()
                ).format(Date())

            // Marcamos el préstamo como devuelto.
            val prestamoActualizado =
                prestamo.copy(
                    devuelto = true,
                    fechaDevolucion = fechaActual
                )

            prestamoRepository.actualizarPrestamo(
                prestamoActualizado
            )

            // Buscamos el libro asociado.
            val libro =
                libroRepository.obtenerLibroPorId(
                    prestamo.idLibro
                )

            // Volvemos a poner el libro como disponible.
            if (libro != null) {

                val libroActualizado =
                    libro.copy(
                        disponible = true
                    )

                libroRepository.actualizarLibro(
                    libroActualizado
                )
            }

            // Actualizamos la lista de préstamos activos.
            _prestamos.value =
                prestamoRepository.obtenerPrestamosActivos()

            // Actualizamos también los libros disponibles.
            val librosActualizados =
                libroRepository.obtenerLibros()

            _librosDisponibles.value =
                librosActualizados.filter {
                    it.disponible
                }
        }
    }
}
