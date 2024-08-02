package choquepata.puma.gitalquiler.inquilinos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import choquepata.puma.gitalquiler.R
import choquepata.puma.gitalquiler.propietarios.InquilinoAdapter
import choquepata.puma.gitalquiler.propietarios.fragment_Informacion_inquilino
import com.google.firebase.database.*

class lista_inquilinos : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: InquilinoAdapter
    private lateinit var inquilinoList: MutableList<inquilinos>
    private lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_inquilinos)

        recyclerView = findViewById(R.id.recyclerViewInquilinos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        inquilinoList = mutableListOf()
        adapter = InquilinoAdapter(inquilinoList) { inquilino ->
            val dialogFragment = fragment_Informacion_inquilino.newInstance(
                inquilino.nombres,
                inquilino.email,
                inquilino.telefono,
                inquilino.propietarioId,
                inquilino.inicioAlquiler,
                inquilino.nrCuarto,
                inquilino.monto
            )
            dialogFragment.show(supportFragmentManager, "InformacionInquilinoFragment")
        }
        recyclerView.adapter = adapter

        mDatabase = FirebaseDatabase.getInstance().reference.child("Inquilinos")
        inquilinos()
    }

    private fun inquilinos() {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                inquilinoList.clear()
                for (dataSnapshot in snapshot.children) {
                    val inquilino = dataSnapshot.getValue(inquilinos::class.java)
                    inquilino?.let { inquilinoList.add(it) }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle possible errors.
            }
        })
    }
}
