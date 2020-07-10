package com.alvaromena.claseroom.ui.update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alvaromena.claseroom.ClaseRoom
import com.alvaromena.claseroom.R
import com.alvaromena.claseroom.model.Deudor
import com.alvaromena.claseroom.model.DeudorDAO
import kotlinx.android.synthetic.main.fragment_update.*

class updateFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_update, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        et_telefono.visibility = View.GONE
        et_cantidad.visibility = View.GONE
        bt_actualizar_update.visibility=View.GONE
        var idDeudor= 0
        val deudorDAO:DeudorDAO = ClaseRoom.database.DeudorDAO()

        bt_buscar_update.setOnClickListener{
            val nombre = et_nombre.text.toString()


            val deudor = deudorDAO.buscarDeudor(nombre)
            if(deudor != null){
                idDeudor=deudor.id
                et_telefono.visibility = View.VISIBLE
                et_cantidad.visibility = View.VISIBLE
                et_telefono.setText(deudor.telefono)
                et_cantidad.setText(deudor.cantidad.toString())
                bt_buscar_update.visibility = View.GONE
                bt_actualizar_update.visibility = View.VISIBLE

            }
        }

        bt_actualizar_update.setOnClickListener{
            val deudor =Deudor(
                idDeudor,
                et_nombre.text.toString()
                ,et_telefono.text.toString()
                ,et_cantidad.text.toString().toLong())
            deudorDAO.actualizarDeudor(deudor)
            et_telefono.visibility = View.GONE
            et_cantidad.visibility = View.GONE
            bt_buscar_update.visibility = View.VISIBLE
            bt_actualizar_update.visibility = View.GONE
        }
    }
}