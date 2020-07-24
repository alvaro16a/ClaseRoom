package com.alvaromena.claseroom

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

        bt_nuevo_usuario.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }

        bt_inicio_sesion.setOnClickListener {
            val email = et_correo.text.toString()
            val password = et_clave.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                tv_consola_login.text = getString(R.string.datos_incompletos)
            } else {
                mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this,
                        OnCompleteListener<AuthResult?> { task ->
                            if (task.isSuccessful) {
                                onBackPressed()
                                startActivity(Intent(this, MainActivity::class.java))
                            } else {
                                Toast.makeText(
                                    this, "Authentication failed.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                Log.w("TAG", "createUserWithEmail:failure", task.exception)
                            }
                        })
            }

        }
    }
}