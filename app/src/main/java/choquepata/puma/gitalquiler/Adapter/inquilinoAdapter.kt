package choquepata.puma.gitalquiler.propietarios

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import choquepata.puma.gitalquiler.R
import choquepata.puma.gitalquiler.inquilinos.inquilinos

class InquilinoAdapter(private val inquilinosList: List<inquilinos>, private val clickListener: (inquilinos) -> Unit) :
    RecyclerView.Adapter<InquilinoAdapter.InquilinoViewHolder>() {

    class InquilinoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView = itemView.findViewById(R.id.tvNombreInquilino)
        val emailTextView: TextView = itemView.findViewById(R.id.tvEmailInquilino)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InquilinoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_inquilino, parent, false)
        return InquilinoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: InquilinoViewHolder, position: Int) {
        val inquilino = inquilinosList[position]
        holder.nombreTextView.text = inquilino.nombres
        holder.emailTextView.text = inquilino.email


        holder.itemView.setOnClickListener { clickListener(inquilino) }
    }

    override fun getItemCount() = inquilinosList.size
}
