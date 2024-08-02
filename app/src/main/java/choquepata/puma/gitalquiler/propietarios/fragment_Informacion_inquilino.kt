package choquepata.puma.gitalquiler.propietarios

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import choquepata.puma.gitalquiler.R

class fragment_Informacion_inquilino : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragmet_informacion_inquilino, container, false)

        val nombre = arguments?.getString("nombre")
        val email = arguments?.getString("email")
        val telefono = arguments?.getString("telefono")
        val propietarioId = arguments?.getString("propietarioId")
        val inicioAlquiler = arguments?.getString("inicioAlquiler")
        val nrCuarto = arguments?.getString("nrCuarto")
        val monto = arguments?.getString("monto")


        view.findViewById<TextView>(R.id.tvNombreInquilinoDialog).text = nombre
        view.findViewById<TextView>(R.id.tvEmailInquilinoDialog).text = email
        view.findViewById<TextView>(R.id.tvTelefonoInquilinoDialog).text = telefono
        view.findViewById<TextView>(R.id.tvPropietarioIdDialog).text = propietarioId
        view.findViewById<TextView>(R.id.tvInicioAlquilerDialog).text = inicioAlquiler
        view.findViewById<TextView>(R.id.tvNrCuartoDialog).text = nrCuarto
        view.findViewById<TextView>(R.id.tvMontoDialog).text = monto

        return view
    }

    companion object {
        fun newInstance(
            nombre: String,
            email: String,
            telefono: String,
            propietarioId: String,
            inicioAlquiler: String,
            nrCuarto: String,
            monto: String
        ): fragment_Informacion_inquilino {
            val fragment = fragment_Informacion_inquilino()
            val args = Bundle()
            args.putString("nombre", nombre)
            args.putString("email", email)
            args.putString("telefono", telefono)
            args.putString("propietarioId", propietarioId)
            args.putString("inicioAlquiler", inicioAlquiler)
            args.putString("nrCuarto", nrCuarto)
            args.putString("monto", monto)
            fragment.arguments = args
            return fragment
        }
    }
}
