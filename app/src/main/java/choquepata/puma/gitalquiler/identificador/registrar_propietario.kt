package choquepata.puma.gitalquiler.identificador

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import choquepata.puma.gitalquiler.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.UserProfileChangeRequest
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import choquepata.puma.gitalquiler.propietarios.pantalla_principal
import kotlin.random.Random

class registrar_propietario : AppCompatActivity() {

    private lateinit var mEditTextGmail: EditText
    private lateinit var mEdittextPassword: EditText
    private lateinit var mEditTextNombre: EditText
    private lateinit var mEditTexttelefono: EditText
    private lateinit var mBtnRegistrar: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference

    private var email: String = ""
    private var password: String = ""
    private var nombres: String = ""
    private var telefono: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrar_propietario)

        mEditTextGmail = findViewById(R.id.editTextIngresarGmail)
        mEdittextPassword = findViewById(R.id.editTextIngresarPassword)
        mEditTextNombre = findViewById(R.id.editTextIngresarNombres)
        mEditTexttelefono = findViewById(R.id.editTextIngresarTelefono)
        mBtnRegistrar = findViewById(R.id.buttonRegistrar)

        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().reference

        mBtnRegistrar.setOnClickListener {
            email = mEditTextGmail.text.toString()
            password = mEdittextPassword.text.toString()
            nombres = mEditTextNombre.text.toString()
            telefono = mEditTexttelefono.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && nombres.isNotEmpty() && telefono.isNotEmpty()) {
                registrarUsuario()
            } else {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        val textRegresar = findViewById<TextView>(R.id.TextViemRegresar)
        textRegresar.setOnClickListener {
            val intentNextActivity = Intent(this, loginPropietario::class.java)
            startActivity(intentNextActivity)
            overridePendingTransition(R.anim.entrada_izquierda, R.anim.salida_derecha)
        }
    }

    private fun registrarUsuario() {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = mAuth.currentUser
                    user?.let {
                        // Generar un ID aleatorio de 6 dígitos
                        val propietarioId = Random.nextInt(100000, 999999).toString()

                        // Actualizar el perfil del usuario
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName("$nombres $telefono")
                            .build()

                        user.updateProfile(profileUpdates)
                            .addOnCompleteListener { profileUpdateTask ->
                                if (profileUpdateTask.isSuccessful) {
                                    // Guardar los datos adicionales en la base de datos
                                    val map: MutableMap<String, Any> = HashMap()
                                    map["email"] = email
                                    map["nombres"] = nombres
                                    map["Telefono"] = telefono
                                    map["contraseña"] = password
                                    map["propietarioId"] = propietarioId

                                    val id = user.uid
                                    mDatabase.child("Dueños").child(id).setValue(map)
                                        .addOnCompleteListener { task2 ->
                                            if (task2.isSuccessful) {
                                                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                                                val intent = Intent(this, pantalla_principal::class.java)
                                                startActivity(intent)
                                                finish()
                                            } else {
                                                Toast.makeText(this, "No se crearon los datos correctamente", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                } else {
                                    Toast.makeText(this, "No se pudo actualizar el perfil", Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                } else {
                    Toast.makeText(this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show()
                }
            }
    }

}
