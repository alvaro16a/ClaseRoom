package com.alvaromena.claseroom.model

import androidx.annotation.RequiresPermission
import androidx.room.*


@Dao
interface DeudorDAO {

    @Insert
    fun crearDeudor(deudor: Deudor)

    @Query("SELECT * FROM tabla_deudor WHERE nombre LIKE :nombre ")
    fun buscarDeudor(nombre: String) : Deudor

    @Update
    fun actualizarDeudor(deudor: Deudor)

    @Delete
    fun borrardeudor(deudor: Deudor)

    @Query("SELECT * FROM tabla_deudor")
    fun getDeudores(): List<Deudor>
}