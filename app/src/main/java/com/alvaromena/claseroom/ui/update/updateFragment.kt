package com.alvaromena.claseroom.ui.update

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.alvaromena.claseroom.ClaseRoom
import com.alvaromena.claseroom.R
import com.alvaromena.claseroom.model.local.DeudorDAO
import com.alvaromena.claseroom.model.remote.DeudorRemote
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_update.*
import java.io.ByteArrayOutputStream

class updateFragment : Fragment() {
    val mStorage = FirebaseStorage.getInstance()
    var idDeudorFirebase: String? = ""
    private val REQUEST_IMAGE_CAPTURE = 1234
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_update, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideDeudorDatosET()
        var idDeudor = 0

        val deudorDAO: DeudorDAO = ClaseRoom.database.DeudorDAO()

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("deudores")

        bt_buscar_update.setOnClickListener {
            val nombre = et_nombre_update.text.toString()
            buscarEnFirebase(nombre, myRef)
        }

        bt_actualizar_update.setOnClickListener {
            //actualizarEnRoom(idDeudor, deudorDAO)
            actualizarEnFirebase(myRef)
            habilitarWidgetsBuscar()
        }
        iv_foto_update.setOnClickListener {
            dispatchTakePictureIntent()
        }
    }

    private fun actualizarEnFirebase(
        myRef: DatabaseReference
    ) {
        var urlPhoto = ""
        val photoRef = mStorage.reference.child(idDeudorFirebase!!)
        photoRef.delete()


        val bitmap = (iv_foto_update.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        var uploadTask = photoRef.putBytes(data)
        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            photoRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                urlPhoto = task.result.toString()
                val childUpdate = HashMap<String, Any>()
                childUpdate["nombre"] = et_nombre_update.text.toString()
                childUpdate["telefono"] = et_telefono_update.text.toString()
                childUpdate["cantidad"] = et_cantidad.text.toString().toLong()
                childUpdate["urlPhoto"] = urlPhoto
                myRef.child(idDeudorFirebase!!).updateChildren(childUpdate)
            }
        }
    }

    private fun habilitarWidgetsBuscar() {
        et_telefono_update.visibility = View.GONE
        et_cantidad.visibility = View.GONE
        iv_foto_update.visibility = View.GONE
        bt_buscar_update.visibility = View.VISIBLE
        bt_actualizar_update.visibility = View.GONE
    }

    private fun buscarEnFirebase(nombre: String, myRef: DatabaseReference) {
        var deudorExiste = false
        val postListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (datasnapshot: DataSnapshot in snapshot.children) {
                    val deudor = datasnapshot.getValue(DeudorRemote::class.java)
                    if (deudor?.nombre == nombre) {
                        deudorExiste = true
                        habilitarWidgetsActualizar(deudor.urlPhoto)
                        et_telefono_update.setText(deudor.telefono)
                        et_cantidad.setText(deudor.cantidad.toString())
                        idDeudorFirebase = deudor.id
                    }
                }
                if (!deudorExiste)
                    Toast.makeText(requireContext(), "Deudor no existe", Toast.LENGTH_SHORT).show()
            }
        }
        myRef.addListenerForSingleValueEvent(postListener)
    }

    private fun habilitarWidgetsActualizar(deudor: String) {
        et_telefono_update.visibility = View.VISIBLE
        et_cantidad.visibility = View.VISIBLE
        iv_foto_update.visibility = View.VISIBLE
        bt_buscar_update.visibility = View.GONE
        bt_actualizar_update.visibility = View.VISIBLE
        Picasso.get().load(deudor).into(iv_foto_update)

    }

    private fun hideDeudorDatosET() {
        et_telefono_update.visibility = View.GONE
        et_cantidad.visibility = View.GONE
        bt_actualizar_update.visibility = View.GONE
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { TakePictureIntent ->
            TakePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                startActivityForResult(TakePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            iv_foto_update.setImageBitmap(imageBitmap)
        }
    }
}