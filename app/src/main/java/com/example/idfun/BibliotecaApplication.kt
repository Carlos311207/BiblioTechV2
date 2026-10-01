package com.example.idfun

import android.app.Application
import com.example.idfun.data.BibliotecaDatabase
import com.example.idfun.data.DatabaseProvider
import com.example.idfun.data.EstudianteRepository
import com.example.idfun.data.LibroRepository
import com.example.idfun.data.PrestamoRepository

class BibliotecaApplication : Application() {

    val database: BibliotecaDatabase by lazy {
        DatabaseProvider.getDatabase(this)
    }

    val prestamoDao
        get() = database.prestamoDao()

    val prestamoRepository: PrestamoRepository by lazy {
        PrestamoRepository(prestamoDao)
    }

    val libroDao
        get() = database.libroDao()

    val libroRepository: LibroRepository by lazy {
        LibroRepository(libroDao)
    }

    val estudianteDao
        get() = database.estudianteDao()

    val estudianteRepository: EstudianteRepository by lazy {
        EstudianteRepository(estudianteDao)
    }
}
