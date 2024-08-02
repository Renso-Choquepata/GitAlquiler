package choquepata.puma.gitalquiler.propietarios

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Shader
import android.net.Uri
import choquepata.puma.gitalquiler.R
import android.os.Bundle
import android.provider.MediaStore
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import choquepata.puma.gitalquiler.inquilinos.DatePickerFragment
import choquepata.puma.gitalquiler.inquilinos.lista_inquilinos
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream

class pantalla_principal : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var calendario: EditText

    private lateinit var nombreTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var idTextView: TextView
    private lateinit var imagePerilPropietario: ImageView

    private val PICK_IMAGE_REQUEST = 71
    private val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_principal)

        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().reference
        storageReference = FirebaseStorage.getInstance().reference.child("profile_images")

        nombreTextView = findViewById(R.id.textView2)
        emailTextView = findViewById(R.id.textView3)
        idTextView = findViewById(R.id.idparainquilinos)
        imagePerilPropietario = findViewById(R.id.imagePerilPropietario)


        val currentUser = mAuth.currentUser
        currentUser?.let {
            val userId = it.uid
            mDatabase.child("Dueños").child(userId).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val nombre = snapshot.child("nombres").value.toString()
                        val email = snapshot.child("email").value.toString()
                        val propietarioId = snapshot.child("propietarioId").value.toString()

                        nombreTextView.text = nombre
                        emailTextView.text = email
                        idTextView.text = "ID: $propietarioId"
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }

        val resultadoInquilino = findViewById<TextView>(R.id.textInquilinosInfo)
        resultadoInquilino.setOnClickListener {
            val intentNextActivity = Intent(this, lista_inquilinos::class.java)
            startActivity(intentNextActivity)
        }

        val textInquilinoInfo = findViewById<ImageView>(R.id.ImageConfiguracionPropietario)
        textInquilinoInfo.setOnClickListener {
            val dialog = setting_pro()
            dialog.show(supportFragmentManager, "")
        }

        imagePerilPropietario.setOnClickListener {
            chooseImage()
        }
    }

    private fun chooseImage() {
        val options = arrayOf<CharSequence>("Tomar Foto", "Elegir de la Galeria", "Cancelar")
        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Agregar foto")
        builder.setItems(options) { dialog, item ->
            when {
                options[item] == "Tomar Foto" -> {
                    val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE)
                }
                options[item] == "Elegir de la Galeria" -> {
                    val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(pickPhoto, PICK_IMAGE_REQUEST)
                }
                options[item] == "Cancelar" -> {
                    dialog.dismiss()
                }
            }
        }
        builder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PICK_IMAGE_REQUEST -> {
                    val selectedImageUri: Uri? = data?.data
                    selectedImageUri?.let {
                        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, it)
                        val resizedBitmap = resizeImage(bitmap)
                        val circularBitmap = getCircularBitmap(resizedBitmap)
                        val uri = getImageUri(circularBitmap)
                        uri?.let {
                            uploadImageToFirebase(it)
                        }
                    }
                }
                REQUEST_IMAGE_CAPTURE -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    val resizedBitmap = resizeImage(imageBitmap)
                    val circularBitmap = getCircularBitmap(resizedBitmap)
                    val uri = getImageUri(circularBitmap)
                    uri?.let {
                        uploadImageToFirebase(it)
                    }
                }
            }
        }
    }

    private fun resizeImage(bitmap: Bitmap): Bitmap {
        val imageViewWidth = imagePerilPropietario.width
        val imageViewHeight = imagePerilPropietario.height
        return Bitmap.createScaledBitmap(bitmap, imageViewWidth, imageViewHeight, false)
    }

    private fun getCircularBitmap(bitmap: Bitmap): Bitmap {
        val size = Math.min(bitmap.width, bitmap.height)
        val x = (bitmap.width - size) / 2
        val y = (bitmap.height - size) / 2

        val squaredBitmap = Bitmap.createBitmap(bitmap, x, y, size, size)
        val circleBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(circleBitmap)

        val paint = Paint()
        val shader = BitmapShader(squaredBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.shader = shader
        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)

        val radius = size / 2f
        canvas.drawCircle(radius, radius, radius, paint)

        return circleBitmap
    }

    private fun getImageUri(bitmap: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(contentResolver, bitmap, "Title", null)
        return Uri.parse(path)
    }

    private fun uploadImageToFirebase(fileUri: Uri) {
        val ref = storageReference.child("${mAuth.currentUser?.uid}.jpg")
        ref.putFile(fileUri)
            .addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener { uri ->
                    imagePerilPropietario.setImageURI(fileUri)
                    val user = mAuth.currentUser
                    user?.let {
                        val databaseReference = FirebaseDatabase.getInstance().getReference("Dueños").child(it.uid)
                        databaseReference.child("profileImageUrl").setValue(uri.toString())
                    }
                }
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }
}
