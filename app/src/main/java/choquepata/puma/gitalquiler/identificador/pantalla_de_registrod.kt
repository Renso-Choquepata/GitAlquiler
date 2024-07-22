package choquepata.puma.gitalquiler.identificador

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import choquepata.puma.gitalquiler.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class pantalla_de_registrod : AppCompatActivity() {

    private lateinit var mEditTextGmail: EditText
    private lateinit var mEdittextPassword: EditText
    private lateinit var mTextRegistroUsuario: TextView
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference

    private var email: String = ""
    private var password: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_de_registrod)

        mEditTextGmail = findViewById(R.id.EditTextGmail)
        mEdittextPassword = findViewById(R.id.EdittextPassword)
        mTextRegistroUsuario = findViewById(R.id.TextRegistroUsuario)

        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().reference

        mTextRegistroUsuario.setOnClickListener {
            email = mEditTextGmail.text.toString()
            password = mEdittextPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                registerUsuario()
            } else {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun registerUsuario() {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val map: MutableMap<String, Any> = HashMap()
                    map["email"] = email
                    map["password"] = password

                    val id = mAuth.currentUser?.uid
                    id?.let {
                        mDatabase.child("Dueños").child(id).setValue(map)
                            .addOnCompleteListener { task2 ->
                                if (task2.isSuccessful) {
                                    Toast.makeText(this, "registro exitoso", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this, pantalla_de_login::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(this, "No se crearon los datos correctamente", Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                } else {
                    Toast.makeText(this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show()
                }
            }
    }

}