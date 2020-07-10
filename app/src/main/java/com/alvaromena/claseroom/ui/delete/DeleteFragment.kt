package com.alvaromena.claseroom.ui.delete

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alvaromena.claseroom.ClaseRoom
import com.alvaromena.claseroom.R
import kotlinx.android.synthetic.main.fragment_delete.*



class DeleteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delete, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bt_eliminar.setOnClickListener{
            val nombre = et_nombre_eliminar.text.toString()

            val deudorDAO = ClaseRoom.database.DeudorDAO()
            val deudor= deudorDAO.buscarDeudor(nombre)

            if(deudor == null){
                tv_eliminar.text=getString(R.string.usuario_no_existe)
            }else{
                deudorDAO.borrardeudor(deudor)
                tv_eliminar.text=getString(R.string.Usuario_eliminado)
            }
        }
    }

}
