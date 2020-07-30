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

    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onStart() {
        super.onStart()
        val user = mAuth.currentUser
        if (user != null) {
            onBackPressed()
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        bt_nuevo_usuario.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }

        bt_inicio_sesion.setOnClickListener {
            val email = et_correo.text.toString()
            val password = et_clave.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                tv_consola_login.text = getString(R.string.datos_incompletos)
            } else {
                singinWhithFireBase(email, password)
            }

        }
    }

    private fun singinWhithFireBase(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this,
                OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {
                        onBackPressed()
                        startActivity(Intent(this, MainActivity::class.java))
                    } else {
                        showMessage("Authentication failed.")
                        Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    }
                })
    }

    private fun showMessage(msg: String) {
        Toast.makeText(
            this, msg,
            Toast.LENGTH_SHORT
        ).show()
    }
}