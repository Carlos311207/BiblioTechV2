package com.example.idfun.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.idfun.BibliotecaApplication
import com.example.idfun.modelo.Estudiante
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EstudianteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository =
        (application as BibliotecaApplication).estudianteRepository

    private val _estudiantes = MutableStateFlow<List<Estudiante>>(emptyList())
    val estudiantes: StateFlow<List<Estudiante>> = _estudiantes.asStateFlow()

    private val _estudianteSeleccionado = MutableStateFlow<Estudiante?>(null)
    val estudianteSeleccionado: StateFlow<Estudiante?> = _estudianteSeleccionado.asStateFlow()

    fun cargarEstudiantes() {
        viewModelScope.launch(Dispatchers.IO) {
            _estudiantes.value = repository.obtenerEstudiantes()
        }
    }

    fun insertarEstudiante(estudiante: Estudiante) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertarEstudiante(estudiante)
            _estudiantes.value = repository.obtenerEstudiantes()
        }
    }

    fun cargarEstudiantePorId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _estudianteSeleccionado.value = repository.obtenerEstudiantePorId(id)
        }
    }
}