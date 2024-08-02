package choquepata.puma.gitalquiler.inquilinos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import choquepata.puma.gitalquiler.R
import choquepata.puma.gitalquiler.identificador.loginPropietario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.*

class registrarInq : AppCompatActivity() {
    private lateinit var mEditTextNombresInq: EditText
    private lateinit var mEditTextGmailInq: EditText
    private lateinit var mEditTextPasswordInq: EditText
    private lateinit var mEditTextCelularInq: EditText
    private lateinit var mEditTextIdpropietario: EditText
    private lateinit var mEditTextInicioAlquiler: EditText
    private lateinit var mEditTextNrCuarto: EditText
    private lateinit var mEditTextPago: EditText
    private lateinit var mButtonRegistrarInq: Button

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference

    private var email: String = ""
    private var password: String = ""
    private var nombres: String = ""
    private var celular: String = ""
    private var idpropietario: String = ""
    private var inicioalquiler: String = ""
    private var nrcuarto: String = ""
    private var pago: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrar_inq)

        mEditTextGmailInq = findViewById(R.id.editTextGmailInq)
        mEditTextPasswordInq = findViewById(R.id.editTextPasswordInq)
        mEditTextNombresInq = findViewById(R.id.editTextNombresInq)
        mEditTextCelularInq = findViewById(R.id.editTextTelefonoInq)
        mEditTextIdpropietario = findViewById(R.id.editTextIdPropietario)
        mEditTextInicioAlquiler = findViewById(R.id.editTextFehaDeComienzo)
        mEditTextNrCuarto = findViewById(R.id.editTextNrCuarto)
        mEditTextPago = findViewById(R.id.editTextpago)
        mButtonRegistrarInq = findViewById(R.id.buttonRegistrarInq)

        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().reference

        val textRegresarInq = findViewById<TextView>(R.id.TextViemRegresarLogin)
        textRegresarInq.setOnClickListener {
            val intentNextActivity = Intent(this, loginInq::class.java)
            startActivity(intentNextActivity)
            overridePendingTransition(R.anim.entrada_izquierda, R.anim.salida_derecha)
        }

        mButtonRegistrarInq.setOnClickListener {
            email = mEditTextGmailInq.text.toString()
            password = mEditTextPasswordInq.text.toString()
            nombres= mEditTextNombresInq.text.toString()
            celular= mEditTextCelularInq.text.toString()
            idpropietario = mEditTextIdpropietario.text.toString()
            inicioalquiler = mEditTextInicioAlquiler.text.toString()
            nrcuarto = mEditTextNrCuarto.text.toString()
            pago = mEditTextPago.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && nombres.isNotEmpty() && celular.isNotEmpty() && idpropietario.isNotEmpty() && inicioalquiler.isNotEmpty() && nrcuarto.isNotEmpty()
                && pago.isNotEmpty())
            {
                verificarIdPropietario()
            } else {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun verificarIdPropietario() {
        // Buscar en todos los nodos de "Dueños" y verificar si el propietarioId existe
        mDatabase.child("Dueños").orderByChild("propietarioId").equalTo(idpropietario)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        registrarInquilino()
                    } else {
                        Toast.makeText(this@registrarInq, "ID de propietario no existe", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@registrarInq, "Error al verificar ID de propietario", Toast.LENGTH_SHORT).show()
                }
            })
    }


    private fun registrarInquilino() {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = mAuth.currentUser
                    user?.let {
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(nombres)
                            .build()

                        user.updateProfile(profileUpdates)
                            .addOnCompleteListener { profileUpdateTask ->
                                if (profileUpdateTask.isSuccessful) {
                                    registrarInquilino(user.uid)
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

    private fun registrarInquilino(userId: String) {
        val map: MutableMap<String, Any> = HashMap()
        map["email"] = email
        map["nombres"] = nombres
        map["Telefono"] = celular
        map["contraseña"] = password
        map["ID del Propietario"] = idpropietario
        map["Inicio del Alquiler"] = inicioalquiler
        map["Nr de Cuarto"] = nrcuarto
        map["Monto"] = pago

        mDatabase.child("Inquilinos").child(userId).setValue(map)
            .addOnCompleteListener { task2 ->
                if (task2.isSuccessful) {
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, pnatalla_principal_inq::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "No se crearon los datos correctamente", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
