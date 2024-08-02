package choquepata.puma.gitalquiler.identificador

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import choquepata.puma.gitalquiler.R
import choquepata.puma.gitalquiler.propietarios.pantalla_principal
import com.google.firebase.auth.FirebaseAuth

class loginPropietario : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        emailField = findViewById(R.id.editTextIngreseGmail)
        passwordField = findViewById(R.id.editTextIngreseContrasena)
        val btnLogin = findViewById<Button>(R.id.buttonIngresar)

        btnLogin.setOnClickListener {
            val email = emailField.text.toString()
            val password = passwordField.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        val btnRegistrar = findViewById<TextView>(R.id.textViewSigRegistrar)
        btnRegistrar.setOnClickListener {
            val intentNextActivity = Intent(this, registrar_propietario::class.java)
            startActivity(intentNextActivity)
        }
    }

    private fun loginUser(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Exito", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, pantalla_principal::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
