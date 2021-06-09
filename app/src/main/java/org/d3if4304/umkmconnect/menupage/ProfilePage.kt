package org.d3if4304.umkmconnect.menupage

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile_page.*
import kotlinx.android.synthetic.main.side_header.*
import org.d3if4304.umkmconnect.R
import java.io.ByteArrayOutputStream

class ProfilePage : Fragment() {

    companion object{
        const val REQUEST_CAMERA = 100
    }

    private lateinit var imageUri: Uri
    lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?):
            View? {
        return inflater.inflate(R.layout.activity_profile_page,container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        val user = auth.currentUser

        if( user != null)
        {
            if( user.photoUrl != null)
            {
                Picasso.get().load(user.photoUrl).into(ivProfile)
            } else {
                Picasso.get().load("https://picsum.photos/seed/picsum/200/300").into(ivProfile)
            }


            etEmail.setText(user.email)

            if (user.isEmailVerified)
            {
                verifiedLogo.visibility = View.VISIBLE
            }

            if(user.phoneNumber.isNullOrEmpty())
            {
                etPhone.setText("082355662323")
            } else {
                etPhone.setText(user.phoneNumber)
            }
        }

        ivProfile.setOnClickListener()
        {
            intentCamera()
        }

        btnUpdate.setOnClickListener()
        {
            val image = when{
                ::imageUri.isInitialized -> imageUri
                user?.photoUrl == null -> Uri.parse("https://picsum.photos/seed/picsum/200/300")
                else -> user.photoUrl
            }
            val name = etName.text.toString().trim()
            val phoneNumber = etPhone.text.toString().trim()
            if(name.isEmpty() && phoneNumber.isEmpty())
            {
                etName.error = "Fill Name Field"
                etName.requestFocus()
                return@setOnClickListener
            }

            UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .setPhotoUri(image)
                    .build().also {
                        user?.updateProfile(it)?.addOnCompleteListener {
                            if(it.isSuccessful)
                            {
                                Toast.makeText(activity,"Process Success",Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(activity,"${it.exception?.message}",Toast.LENGTH_LONG).show()
                            }
                        }
                    }
        }
    }

    private fun intentCamera()
    {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            activity?.packageManager?.let {
                intent.resolveActivity(it).also {
                    startActivityForResult(intent, REQUEST_CAMERA)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CAMERA && resultCode == RESULT_OK)
        {
            val Image_Bitmap = data?.extras?.get("data") as Bitmap
            uploadImage(Image_Bitmap)
        }
    }

    private fun uploadImage(imageBitmap: Bitmap) {
        val baos = ByteArrayOutputStream()
        val ref = FirebaseStorage.getInstance().reference.child("img/${FirebaseAuth.getInstance().currentUser?.uid}")

        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val image = baos.toByteArray()

        ref.putBytes(image)
                .addOnCompleteListener {
                    if(it.isSuccessful)
                    {
                        ref.downloadUrl.addOnCompleteListener {
                            it.result?.let{
                                imageUri = it
                                ivProfile.setImageBitmap(imageBitmap)
                            }
                        }
                    }
                }
    }
}