package choquepata.puma.gitalquiler.propietarios

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import choquepata.puma.gitalquiler.R
import choquepata.puma.gitalquiler.identificador.pantalla_de_elegirud
import choquepata.puma.gitalquiler.inquilinos.loginInq

class setting_pro : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.activity_configuracion_pro, null)
        dialog.setContentView(view)

        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val borrarCuentaButton = view.findViewById<TextView>(R.id.unicazzzzzzzzzzzzzzzzzzz)
        borrarCuentaButton.setOnClickListener {

        }

        val cerrarsesion = view.findViewById<TextView>(R.id.unicawwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww)
        cerrarsesion.setOnClickListener {
            val intentNextActivity = Intent(requireActivity(), pantalla_de_elegirud::class.java)
            startActivity(intentNextActivity)
        }
    }
}
