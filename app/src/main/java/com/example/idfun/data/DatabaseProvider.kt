package com.example.idfun.data

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object DatabaseProvider {

    @Volatile
    private var INSTANCE: BibliotecaDatabase? = null

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("DROP TABLE IF EXISTS `prestamos`")
            db.execSQL(
                """
                CREATE TABLE IF NOT EXISTS `prestamos`(
                    `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    `idEstudiante` INTEGER NOT NULL,
                    `idLibro` INTEGER NOT NULL,
                    `fechaPrestamo` TEXT NOT NULL,
                    `fechaDevolucion` TEXT,
                    `devuelto` INTEGER NOT NULL,
                    `fechaLimite` TEXT NOT NULL,
                    FOREIGN KEY(`idEstudiante`) REFERENCES `estudiantes`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE,
                    FOREIGN KEY(`idLibro`) REFERENCES `libros`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE
                )
                """.trimIndent()
            )
        }
    }

    fun getDatabase(context: Context): BibliotecaDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                BibliotecaDatabase::class.java,
                "bibliotech_database"
            )
                .addMigrations(MIGRATION_1_2)
                .fallbackToDestructiveMigration()
                .build()

            INSTANCE = instance
            instance
        }
    }
}
