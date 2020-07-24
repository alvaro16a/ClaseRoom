package com.alvaromena.claseroom

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_registro.*


class RegistroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

        bt_guardar.setOnClickListener {

            val nombre = et_nombre.text.toString()
            val email = et_correo.text.toString()
            val clave = et_clave.text.toString()
            val rclave = et_repetir_clave.text.toString()
            val cresidencia = sp_lugar_naciminto.selectedItem.toString()

            if (clave == rclave && clave.isNotEmpty()) {
                if (nombre.isEmpty() || email.isEmpty() || cresidencia.isEmpty()) {
                    tv_resultados.text = getString(R.string.datos_incompletos)
                } else {
                    val password = clave

                    mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(
                            this
                        ) { task ->
                            if (task.isSuccessful) {
                                //createUsuarioEnBaseDeDatos()
                                Toast.makeText(
                                    this, "Registro exitoso.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                onBackPressed()
                            } else {
                                Toast.makeText(
                                    this, "El registro a fallado.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                Log.w("TAG", "createUserWithEmail:failure", task.exception)
                            }
                        }
                }
            } else
                tv_resultados.text = getString(R.string.no_contrasena)
        }
    }

    private fun createUsuarioEnBaseDeDatos() {
        TODO("Not yet implemented")
    }
}

