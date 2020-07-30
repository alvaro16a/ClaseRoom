package com.alvaromena.claseroom.ui.create

import android.app.Activity.RESULT_OK
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
import com.alvaromena.claseroom.R
import com.alvaromena.claseroom.model.remote.DeudorRemote
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_create.*
import java.io.ByteArrayOutputStream

class CreateFragment : Fragment() {
    private val REQUEST_IMAGE_CAPTURE = 1234
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iv_foto.setOnClickListener {
            dispatchTakePictureIntent()
        }
        bt_guardar.setOnClickListener {
            val nombre = et_nombre_update.text.toString()
            val telefono = et_telefono_update.text.toString()
            val cantidad = et_cantidad.text.toString()
            if (nombre.isEmpty() || telefono.isEmpty() || cantidad.isEmpty()) {
                showMessage("por favor ingrese todos los datos")
            } else {
                GuardarEnFirebase(nombre, telefono, cantidad.toLong())
                cleanEditText()
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { TakePictureIntent ->
            TakePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                startActivityForResult(TakePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            iv_foto.setImageBitmap(imageBitmap)
        }
    }


    private fun GuardarEnFirebase(nombre: String, telefono: String, cantidad: Long) {

        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef: DatabaseReference = database.getReference("deudores")
        val id = myRef.push().key

        val mStorage = FirebaseStorage.getInstance()
        val photoRef = mStorage.reference.child(id!!)
        var urlPhoto = ""

        iv_foto.isDrawingCacheEnabled = true
        iv_foto.buildDrawingCache()
        val bitmap = (iv_foto.drawable as BitmapDrawable).bitmap
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
                val deudor = DeudorRemote(
                    id,
                    nombre,
                    telefono,
                    cantidad,
                    urlPhoto
                )
                myRef.child(id).setValue(deudor)
            } else {
                // Handle failures
                // ...
            }
        }
    }

    private fun cleanEditText() {
        et_nombre_update.setText("")
        et_telefono_update.setText("")
        et_cantidad.setText("")
    }

    private fun showMessage(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }
}

