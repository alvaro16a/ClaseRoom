package com.alvaromena.claseroom.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabla_usuarios")
class Usuario(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name="id") val id: Int?,
    @ColumnInfo(name = "nombre") val nombre: String,
    @ColumnInfo(name = "correo") val correo: String,
    @ColumnInfo(name = "ciudad_residencia") val ciudad_residencia: String,
    @ColumnInfo(name = "clave") val clave: String
)
