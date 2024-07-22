package choquepata.puma.gitalquiler.identificador

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import choquepata.puma.gitalquiler.R



class pantalla_de_login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pantalla_de_login)

        val btnSiguiente = findViewById<TextView>(R.id.btnSigRegistro)
        btnSiguiente.setOnClickListener {
            val intentNextActivity = Intent(this, pantalla_de_registrod::class.java)
            startActivity(intentNextActivity)
        }
    }
}
