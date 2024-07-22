package choquepata.puma.gitalquiler.LAYOUT

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import choquepata.puma.gitalquiler.R
import choquepata.puma.gitalquiler.identificador.pantalla_de_login

class pantalla_de_elegir : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pantalla_de_elegir)


        val btnElegir= findViewById<TextView>(R.id.btnDueño)
        btnElegir.setOnClickListener(){
            val intentNextActivity = Intent(this, pantalla_de_login::class.java)
            startActivity(intentNextActivity)
        }

    }
}