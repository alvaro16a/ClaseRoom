package com.alvaromena.claseroom.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alvaromena.claseroom.ClaseRoom
import com.alvaromena.claseroom.R
import com.alvaromena.claseroom.model.Deudor
import com.alvaromena.claseroom.model.DeudorDAO
import kotlinx.android.synthetic.main.fragment_create.*
import java.lang.NullPointerException
import java.sql.Types.NULL

class CreateFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_create, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bt_guardar.setOnClickListener{
            val nombre= et_nombre.text.toString()
            val telefono=et_telefono.text.toString()
            val cantidad = et_cantidad.text.toString().toLong()
            val deudor = Deudor(NULL,nombre,telefono,cantidad)

            val deudorDAO : DeudorDAO = ClaseRoom.database.DeudorDAO()
            deudorDAO.crearDeudor(deudor)

            et_nombre.setText("")
            et_telefono.setText("")
            et_cantidad.setText("")
        }
    }
}