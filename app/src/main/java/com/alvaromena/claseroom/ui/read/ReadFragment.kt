package com.alvaromena.claseroom.ui.read

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.alvaromena.claseroom.ClaseRoom
import com.alvaromena.claseroom.R
import com.alvaromena.claseroom.model.DeudorDAO
import kotlinx.android.synthetic.main.fragment_read.*

class ReadFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_read, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bt_buscar.setOnClickListener{
            val nombre=et_nombre.text.toString()
            val deudorDAO: DeudorDAO = ClaseRoom.database.DeudorDAO()
            val deudor = deudorDAO.buscarDeudor(nombre)

            if(deudor != null){
                tv_resultado.text = "Nombre: ${deudor.nombre}\n Telefono= ${deudor.telefono}\n Cantidad= ${deudor.cantidad} "
            }else{
                Toast.makeText(context,"Deudor no existe",Toast.LENGTH_SHORT).show()
            }
        }
    }
}