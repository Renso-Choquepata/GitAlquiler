package choquepata.puma.gitalquiler.LAYOUT

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import choquepata.puma.gitalquiler.R
import android.os.Handler
import android.os.Looper
import android.widget.Button

class Pantalla_de_inicio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pantalla_de_inicio)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent=Intent(this, pantalla_de_bienvenido::class.java)
            startActivity(intent)
            finish()
        }, 3000)


    }


}