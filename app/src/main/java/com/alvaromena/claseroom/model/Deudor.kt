package com.alvaromena.claseroom.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabla_deudor")
class Deudor(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "nombre")val nombre: String,
    @ColumnInfo(name = "telefono")val telefono: String,
    @ColumnInfo(name = "cantidad")val cantidad:Long
)
