package choquepata.puma.gitalquiler.inquilinos
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import choquepata.puma.gitalquiler.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.DatabaseReference
import settingFragmentDialogInquilino

class pnatalla_principal_inq : AppCompatActivity() {
    private lateinit var etDate: EditText
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private lateinit var mTextViewNombreInq: TextView
    private lateinit var mTextViewGmailInq: TextView
    private lateinit var mTextViewNombresPro: TextView
    private lateinit var mTextViewGmailPro: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pnatalla_principal_inq)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        etDate = findViewById(R.id.etDate)
        etDate.setOnClickListener { showDatePickerDialog()}

        mTextViewNombreInq = findViewById(R.id.textNombreInquilinoP)
        mTextViewGmailInq = findViewById(R.id.textView3)
        mTextViewNombresPro = findViewById(R.id.textNombrePropietario)
        mTextViewGmailPro = findViewById(R.id.textGmailPropietario)

        val currentUser = auth.currentUser
        currentUser?.let {
            val userId = it.uid
            pasarDatosInquilinos(userId)
        }


        val textInquilinoInfo =findViewById<ImageView>(R.id.imageView14)
        textInquilinoInfo.setOnClickListener {
            val dialog = settingFragmentDialogInquilino()
            dialog.show(supportFragmentManager, "")
        }

    }

    private fun pasarDatosInquilinos(userId: String) {
        database.child("Inquilinos").child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val tenantName = dataSnapshot.child("nombres").getValue(String::class.java)
                val tenantEmail = dataSnapshot.child("email").getValue(String::class.java)
                val landlordId = dataSnapshot.child("ID del Propietario").getValue(String::class.java)

                mTextViewNombreInq.text = tenantName
                mTextViewGmailInq.text = tenantEmail

                landlordId?.let {
                    pasarDatosPropietario(it)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    private fun pasarDatosPropietario(propietarioId: String) {
        database.child("Dueños").orderByChild("propietarioId").equalTo(propietarioId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val landlordName = snapshot.child("nombres").getValue(String::class.java)
                    val landlordEmail = snapshot.child("email").getValue(String::class.java)

                    mTextViewNombresPro.text = landlordName
                    mTextViewGmailPro.text = landlordEmail
                    break
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(supportFragmentManager, "datePicker")
    }

    private fun onDateSelected(day: Int, month: Int, year: Int) {

    }
}
