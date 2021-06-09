package org.d3if4304.umkmconnect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_registration_page.*

class RegistrationPage : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var dataBaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_page)

        auth = Firebase.auth

        reg_Button.setOnClickListener()
        {
            val UserName = createFullName.text.toString()
            val Email = createEmail.text.toString()
            val Password = createPassword.text.toString()

            if(UserName.isEmpty()  && Email.isEmpty() && Password.isEmpty())
            {
                Toast.makeText(this,"Input Tidak Boleh Kosong", Toast.LENGTH_LONG).show()
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(createEmail.text.toString()).matches()) {
                Toast.makeText(this,"Email Tidak Valid", Toast.LENGTH_LONG).show()
            }
            if(Password.length < 10) {
                Toast.makeText(this,"Password Minimal 10 Karakter", Toast.LENGTH_LONG).show()
            }

            // Run Registraion Function
            registration(UserName,Email,Password)
        }

        goToLogin.setOnClickListener()
        {
            val Intent = Intent(this, MainActivity::class.java)
            startActivity(Intent)
        }
    }

    private fun registration(userName:String, email:String, password:String)
    {


        // Email and Password Verification
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val currentUser = auth.currentUser
                    val userID:String = currentUser!!.uid

                    dataBaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userID)

                    var hashMap:HashMap<String,String> = HashMap()
                    hashMap.put("userID",userID)
                    hashMap.put("userName",userName)
                    hashMap.put("imageProfile","")

                    dataBaseReference.setValue(hashMap).addOnCompleteListener(this)
                    {
                        if (it.isSuccessful) {
                            // Open Home Activity
                            Intent(this@RegistrationPage, UMKMPage::class.java).also { intent ->
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(this, "Registration Failed", Toast.LENGTH_LONG).show()
                        }
                    }

                }
            }
    }
}