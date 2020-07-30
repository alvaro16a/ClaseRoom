package com.alvaromena.claseroom.ui.delete

import android.app.AlertDialog
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
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_delete.*


class DeleteFragment : Fragment() {
    val mStorage = FirebaseStorage.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delete, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bt_eliminar.setOnClickListener {
            val nombre = et_nombre_eliminar.text.toString()

            borrarFirebase(nombre)
        }
    }

    private fun borrarFirebase(nombre: String) {
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
                        val alertDialog: AlertDialog? = activity?.let {
                            val builder = AlertDialog.Builder(it)
                            builder.apply {
                                setMessage("Seguro de eliminar el deudor $nombre?")
                                setPositiveButton("Aceptar") { dialog, id ->
                                    myRef.child(deudor.id!!).removeValue()
                                    val photoRef = mStorage.reference.child(deudor.id)
                                    photoRef.delete()
                                }
                                setNegativeButton("Cancelar") { dialog, id -> }
                            }
                            builder.create()
                        }
                        alertDialog?.show()
                    }
                }
                if (!deudorExiste)
                    Toast.makeText(requireContext(), "Deudor no existe", Toast.LENGTH_SHORT).show()
            }
        }
        myRef.addValueEventListener(postListener)
    }

}
