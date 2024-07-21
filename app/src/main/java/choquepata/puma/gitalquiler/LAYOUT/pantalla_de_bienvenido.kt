package choquepata.puma.gitalquiler.LAYOUT

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import choquepata.puma.gitalquiler.R

class pantalla_de_bienvenido : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pantalla_de_bienvenido)

        val btnSiguiente= findViewById<Button>(R.id.btnpantallaDeBienvenido)
        btnSiguiente.setOnClickListener(){
            val intentNextActivity = Intent(this, pantalla_de_elegir::class.java)
            startActivity(intentNextActivity)
        }

    }
}