package choquepata.puma.gitalquiler.identificador

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import choquepata.puma.gitalquiler.R

class loginPropietario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        val btnSiguiente = findViewById<TextView>(R.id.textViewSigRegistrar)
        btnSiguiente.setOnClickListener {
            val intentNextActivity = Intent(this, registrar_propietario::class.java)
            startActivity(intentNextActivity)
        }
    }
}