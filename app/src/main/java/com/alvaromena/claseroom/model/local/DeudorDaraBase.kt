package com.alvaromena.claseroom.model.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = arrayOf(
        Deudor::class,
        Usuario::class
    ), version = 1
)
abstract class DeudorDaraBase : RoomDatabase() {

    abstract fun DeudorDAO() : DeudorDAO
    abstract fun UsuarioDAO() : UsuarioDAO

}