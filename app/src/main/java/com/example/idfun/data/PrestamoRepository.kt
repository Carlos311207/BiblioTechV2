package com.example.idfun.data

import com.example.idfun.modelo.Prestamo

class PrestamoRepository(private val prestamoDao: PrestamoDao) {
    fun insertarPrestamo(prestamo: Prestamo): Long {
        return prestamoDao.insertarPrestamo(prestamo)
    }

    fun obtenerPrestamosActivos(): List<Prestamo> {
        return prestamoDao.obtenerPrestamosActivos()
    }

    fun obtenerPrestamoPorId(id: Int): Prestamo? {
        return prestamoDao.obtenerPrestamoPorId(id)
    }

    fun actualizarPrestamo(prestamo: Prestamo) {
        prestamoDao.actualizarPrestamo(prestamo)
    }

    fun eliminarPrestamo(prestamo: Prestamo) {
        prestamoDao.eliminarPrestamo(prestamo)
    }
}