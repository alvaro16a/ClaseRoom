package com.alvaromena.claseroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alvaromena.claseroom.model.DeudorDAO
import com.alvaromena.claseroom.model.Usuario
import com.alvaromena.claseroom.model.UsuarioDAO
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        /*val datosRecibidos = intent.extras
        val correo = datosRecibidos?.getString("correo")
        val clave = datosRecibidos?.getString("clave")*/
        bt_nuevo_usuario.setOnClickListener {
            onBackPressed()//limpio la pila de actividades para no regresar
            val intent = Intent(this, RegistroActivity::class.java )
            startActivity(intent)
        }

        bt_inicio_sesion.setOnClickListener {
            val correo: String
            val clave :String
            val clave_in : String
            correo=et_correo.text.toString()
            clave_in=et_clave.text.toString()
            if (correo.isEmpty() || clave_in.isEmpty()) {
                tv_consola_login.text=getString(R.string.Datos_incompletos)
            }else{
                val usuarioDAO: UsuarioDAO = ClaseRoom.database.UsuarioDAO()
                val usuario = usuarioDAO.validarusuario(correo)
                if(usuario != null){
                    clave=usuario.clave
                    if (clave_in==clave) {
                        onBackPressed()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }else
                        tv_consola_login.text=getString(R.string.contrasena_mal)
                }else{
                    tv_consola_login.text=getString(R.string.no_existe)
                }
            }

        }

    }
}