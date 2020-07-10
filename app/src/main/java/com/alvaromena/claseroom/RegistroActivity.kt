package com.alvaromena.claseroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alvaromena.claseroom.model.Usuario
import com.alvaromena.claseroom.model.UsuarioDAO
import kotlinx.android.synthetic.main.activity_registro.*


class RegistroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

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
                    val usuario = Usuario(null, nombre, email, cresidencia, clave)
                    val usuarioDAO: UsuarioDAO = ClaseRoom.database.UsuarioDAO()
                    val usuario2 = usuarioDAO.validarusuario(email)
                    if (usuario2 == null) {
                        usuarioDAO.crearUsuario(usuario)
                        onBackPressed()
                        val intent: Intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        tv_resultados.text = getString(R.string.correo_usado)
                    }
                }
            } else
                tv_resultados.text = getString(R.string.no_contrasena)
        }
    }
}

