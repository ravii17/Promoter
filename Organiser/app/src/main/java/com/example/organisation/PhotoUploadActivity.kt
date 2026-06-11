package com.example.organisation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.organisation.data.UserSession
import com.example.organisation.data.api.ProfileRequest
import com.example.organisation.data.api.RetrofitClient
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

class PhotoUploadActivity : AppCompatActivity() {

    private lateinit var ivAvatar: ImageView
    private var selectedUri: Uri? = null

    private val requestCameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            takePhotoLauncher.launch(null)
        } else {
            Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    private val takePhotoLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            ivAvatar.setImageBitmap(bitmap)
            ivAvatar.scaleType = ImageView.ScaleType.CENTER_CROP
            val file = File(cacheDir, "profile_camera.jpg")
            FileOutputStream(file).use { out ->
                bitmap.compress(android.graphics.Bitmap.CompressFormat.JPEG, 90, out)
            }
            selectedUri = Uri.fromFile(file)
        }
    }

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            selectedUri = uri
            ivAvatar.setImageURI(uri)
            ivAvatar.scaleType = ImageView.ScaleType.CENTER_CROP
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_upload)
        UserSession.init(this)

        ivAvatar = findViewById(R.id.ivAvatar)

        findViewById<ImageView>(R.id.btnBack).setOnClickListener { finish() }

        findViewById<LinearLayout>(R.id.btnGallery).setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        findViewById<LinearLayout>(R.id.btnCamera).setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                takePhotoLauncher.launch(null)
            } else {
                requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }

        findViewById<Button>(R.id.btnCompleteKYC).setOnClickListener {
            saveProfile()
        }
    }

    private fun saveProfile() {
        val name = UserSession.userName.orEmpty()
        val email = UserSession.userEmail.orEmpty()
        val city = UserSession.userCity.orEmpty()

        if (name.isBlank() || city.isBlank()) {
            Toast.makeText(this, "Complete personal details first", Toast.LENGTH_SHORT).show()
            return
        }

        setLoading(true)
        lifecycleScope.launch {
            try {
                val api = RetrofitClient.getApi(this@PhotoUploadActivity)
                var photoUrl = UserSession.profilePhotoUrl.orEmpty()

                selectedUri?.let { uri ->
                    val file = uriToFile(uri)
                    val body = file.asRequestBody("image/*".toMediaTypeOrNull())
                    val part = MultipartBody.Part.createFormData("photo", file.name, body)
                    val upload = api.uploadPhoto(part)
                    photoUrl = upload.profile_photo_url
                    UserSession.profilePhotoUrl = photoUrl
                }

                val response = api.saveProfile(
                    ProfileRequest(
                        name = name,
                        email = email,
                        city = city,
                        profile_photo_url = photoUrl
                    )
                )

                UserSession.userName = response.name
                UserSession.userEmail = response.email
                UserSession.userCity = response.city
                UserSession.kycStatus = response.kyc_status
                UserSession.profilePhotoUrl = response.profile_photo_url

                val next = if (response.kyc_status == "approved") {
                    Intent(this@PhotoUploadActivity, HomeActivity::class.java)
                } else {
                    Intent(this@PhotoUploadActivity, KYCActivity::class.java)
                }
                startActivity(next)
                finishAffinity()
            } catch (e: Exception) {
                Toast.makeText(this@PhotoUploadActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            } finally {
                setLoading(false)
            }
        }
    }

    private fun uriToFile(uri: Uri): File {
        val file = File(cacheDir, "profile_upload.jpg")
        contentResolver.openInputStream(uri)?.use { input ->
            FileOutputStream(file).use { output -> input.copyTo(output) }
        }
        return file
    }

    private fun setLoading(loading: Boolean) {
        findViewById<ProgressBar>(R.id.progressBar)?.visibility =
            if (loading) View.VISIBLE else View.GONE
        findViewById<Button>(R.id.btnCompleteKYC).isEnabled = !loading
    }
}
