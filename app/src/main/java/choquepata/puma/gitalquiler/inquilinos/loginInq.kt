package choquepata.puma.gitalquiler.inquilinos



import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import choquepata.puma.gitalquiler.R
import choquepata.puma.gitalquiler.propietarios.pantalla_principal
import com.google.firebase.auth.FirebaseAuth


class loginInq : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mEditTextGmailInq: EditText
    private lateinit var mEdittextPasswordInq: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login_inq)

        mAuth = FirebaseAuth.getInstance()

        mEditTextGmailInq = findViewById(R.id.editTextIngreseGmailInq)
        mEdittextPasswordInq = findViewById(R.id.editTextIngreseContrasenaInq)

        val button = findViewById<Button>(R.id.buttonIngresarInq)
        button.setOnClickListener {
            val email = mEditTextGmailInq.text.toString()
            val password = mEdittextPasswordInq.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginInquilino(email, password)
            } else {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        val TextInquilino = findViewById<TextView>(R.id.textViewSigRegistrarInq)
        TextInquilino.setOnClickListener() {
            val intentNextActivity = Intent(this, registrarInq::class.java)
            startActivity(intentNextActivity)


        }
    }

    private fun loginInquilino(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Autenticación exitosa", Toast.LENGTH_SHORT).show()
                    // Puedes redirigir al usuario a la pantalla principal o a la pantalla que desees
                    val intent = Intent(this, pnatalla_principal_inq::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Error en la autenticación", Toast.LENGTH_SHORT).show()
                }
            }
    }
}