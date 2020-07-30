package com.alvaromena.claseroom.model.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface UsuarioDAO {
    @Insert
    fun crearUsuario(usuario: Usuario)

    @Query("SELECT * FROM tabla_usuarios WHERE correo LIKE :correo")
    fun validarusuario(correo : String) : Usuario
}