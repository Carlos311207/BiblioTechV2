package com.example.idfun.data

import com.example.idfun.modelo.Estudiante

class EstudianteRepository(
    private val estudianteDao: EstudianteDao
) {
    fun insertarEstudiante(estudiante: Estudiante): Long {
        return estudianteDao.insertarEstudiante(estudiante)
    }

    fun actualizarEstudiante(estudiante: Estudiante) {
        estudianteDao.actualizarEstudiante(estudiante)
    }

    fun eliminarEstudiante(estudiante: Estudiante) {
        estudianteDao.eliminarEstudiante(estudiante)
    }

    fun obtenerEstudiantes(): List<Estudiante> {
        return estudianteDao.obtenerEstudiantes()
    }

    fun obtenerEstudiantePorId(id: Int): Estudiante? {
        return estudianteDao.obtenerEstudiantePorId(id)
    }
}
