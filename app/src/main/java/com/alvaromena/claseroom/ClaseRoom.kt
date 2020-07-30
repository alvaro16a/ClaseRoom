package com.alvaromena.claseroom

import android.app.Application
import androidx.room.Room
import com.alvaromena.claseroom.model.local.DeudorDaraBase

class ClaseRoom : Application() {
    companion object{
        lateinit var database: DeudorDaraBase
    }

    override fun onCreate() {
        super.onCreate()

        database= Room.databaseBuilder(
            this,
            DeudorDaraBase::class.java,
            "misdeudores_db"
        ).allowMainThreadQueries()
            .build()
    }
}