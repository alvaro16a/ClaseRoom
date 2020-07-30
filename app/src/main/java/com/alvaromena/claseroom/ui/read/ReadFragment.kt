package com.alvaromena.claseroom.ui.read

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.alvaromena.claseroom.R
import com.alvaromena.claseroom.model.remote.DeudorRemote
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_read.*

class ReadFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_read, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bt_buscar.setOnClickListener {
            tv_resultado_read.text = "resultado"
            var nombre = et_nombre_read.text.toString()
            buscarFirebase(nombre)
        }
    }

    private fun buscarFirebase(nombre: String) {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("deudores")
        var deudorExiste = false
        val postListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (datasnapshot: DataSnapshot in snapshot.children) {
                    val deudor = datasnapshot.getValue(DeudorRemote::class.java)
                    if (deudor?.nombre == nombre) {
                        deudorExiste = true
                        mostrarDeudor(
                            deudor.nombre,
                            deudor.telefono,
                            deudor.cantidad,
                            deudor.urlPhoto
                        )
                    }
                }
                if (!deudorExiste)
                    Toast.makeText(requireContext(), "Deudor no existe", Toast.LENGTH_SHORT).show()
            }
        }
        myRef.addValueEventListener(postListener)
    }

    private fun mostrarDeudor(
        nombre: String,
        telefono: String,
        cantidad: Long,
        urlPhoto: String
    ) {
        Picasso.get().load(urlPhoto).into(iv_foto_read)
        tv_resultado_read.text =
            "Nombre: ${nombre}\n Telefono= ${telefono}\n Cantidad= ${cantidad} "
    }
}