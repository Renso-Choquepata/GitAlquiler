package choquepata.puma.gitalquiler.identificador

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import choquepata.puma.gitalquiler.R
import choquepata.puma.gitalquiler.inquilinos.loginInq

class pantalla_de_elegirud : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pantalla_de_elegirud)
        val btnElegir= findViewById<TextView>(R.id.Textpropietario)
        btnElegir.setOnClickListener(){
            val intentNextActivity = Intent(this, loginPropietario::class.java)
            startActivity(intentNextActivity)
        }

        val TextInquilino= findViewById<TextView>(R.id.textInquilino)
        TextInquilino.setOnClickListener(){
            val intentNextActivity = Intent(this, loginInq::class.java)
            startActivity(intentNextActivity)
        }
    }
}