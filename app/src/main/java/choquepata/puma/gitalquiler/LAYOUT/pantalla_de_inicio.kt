package choquepata.puma.gitalquiler.LAYOUT

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import choquepata.puma.gitalquiler.R
import choquepata.puma.gitalquiler.identificador.pantalla_de_elegirud

class pantalla_de_inicio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pantalla_de_inicio)

        Handler(Looper.getMainLooper()).postDelayed({
            val intentNextActivity = Intent(this, pantalla_de_Bienvenidow::class.java)
            startActivity(intentNextActivity)
            finish() // Llamar a finish() para que la actividad actual se cierre
        }, 5000)

    }
}