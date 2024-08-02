import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import choquepata.puma.gitalquiler.R
import choquepata.puma.gitalquiler.identificador.pantalla_de_elegirud
import choquepata.puma.gitalquiler.inquilinos.pnatalla_principal_inq
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class settingFragmentDialogInquilino : DialogFragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings_dialog_inquilinos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        val btnEliminarCuenta = view.findViewById<Button>(R.id.unico)
        btnEliminarCuenta.setOnClickListener {
            eliminarCuenta()
        }
        val btnCerrarSesion = view.findViewById<Button>(R.id.unico2)
        btnCerrarSesion.setOnClickListener {
            val intent = Intent(activity, pantalla_de_elegirud::class.java)
            startActivity(intent)
        }


    }


    private fun eliminarCuenta() {
        val user = auth.currentUser
        user?.let {
            val userId = it.uid

            it.delete()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val database = FirebaseDatabase.getInstance().reference
                        database.child("Inquilinos").child(userId).removeValue()
                            .addOnCompleteListener { dbTask ->
                                if (dbTask.isSuccessful) {

                                    Toast.makeText(requireContext(), "Cuenta eliminada", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(activity, pantalla_de_elegirud::class.java)
                                    startActivity(intent)
                                    dismiss()
                                } else {
                                    Toast.makeText(requireContext(), "Error al eliminar datos de la cuenta", Toast.LENGTH_SHORT).show()
                                }
                            }
                    } else {
                        Toast.makeText(requireContext(), "Error al eliminar la cuenta", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
