package com.example.idfun.data

import com.example.idfun.modelo.Libro

class LibroRepository(
    private val libroDao: LibroDao
) {
    fun insertarLibro(libro: Libro): Long {
        return libroDao.insertarLibro(libro)
    }

    fun obtenerLibros(): List<Libro> {
        return libroDao.obtenerLibros()
    }

    fun obtenerLibroPorId(id: Int): Libro? {
        return libroDao.obtenerLibroPorId(id)
    }
}