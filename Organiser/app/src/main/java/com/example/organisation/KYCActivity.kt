package com.example.organisation

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class KYCActivity : AppCompatActivity() {

    private lateinit var ivAadhaarFront: ImageView
    private lateinit var layoutUploadFront: LinearLayout
    private lateinit var ivAadhaarBack: ImageView
    private lateinit var layoutUploadBack: LinearLayout

    private var isUploadingFront = true

    private val pickImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val imageUri: Uri? = data?.data
            val imageBitmap = data?.extras?.get("data") as? Bitmap

            if (isUploadingFront) {
                if (imageUri != null) {
                    ivAadhaarFront.setImageURI(imageUri)
                } else if (imageBitmap != null) {
                    ivAadhaarFront.setImageBitmap(imageBitmap)
                }
                ivAadhaarFront.visibility = View.VISIBLE
                layoutUploadFront.visibility = View.GONE
            } else {
                if (imageUri != null) {
                    ivAadhaarBack.setImageURI(imageUri)
                } else if (imageBitmap != null) {
                    ivAadhaarBack.setImageBitmap(imageBitmap)
                }
                ivAadhaarBack.visibility = View.VISIBLE
                layoutUploadBack.visibility = View.GONE
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kyc)

        ivAadhaarFront = findViewById(R.id.ivAadhaarFront)
        layoutUploadFront = findViewById(R.id.layoutUploadFront)
        ivAadhaarBack = findViewById(R.id.ivAadhaarBack)
        layoutUploadBack = findViewById(R.id.layoutUploadBack)

        findViewById<View>(R.id.btnUploadFront).setOnClickListener {
            isUploadingFront = true
            showImagePickerOptions()
        }

        findViewById<View>(R.id.btnUploadBack).setOnClickListener {
            isUploadingFront = false
            showImagePickerOptions()
        }

        findViewById<View>(R.id.btnBack).setOnClickListener {
            finish()
        }

        findViewById<View>(R.id.btnNext).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showImagePickerOptions() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        
        val chooser = Intent.createChooser(galleryIntent, "Select Image")
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(cameraIntent))
        
        pickImage.launch(chooser)
    }
}