package org.d3if4304.umkmconnect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_registration_page.*
import org.d3if4304.umkmconnect.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        loginButton.setOnClickListener()
        {
            login()
        }

        goToCreate.setOnClickListener()
        {
            val Intent = Intent(this, RegistrationPage::class.java)
            startActivity(Intent)
        }
    }

    private fun login()
    {
        if(addEmailLogin.text.toString().isEmpty() && addPasswordValue.text.toString().isEmpty())
        {
            Toast.makeText(this,"Input Tidak Boleh Kosong", Toast.LENGTH_LONG).show()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(addEmailLogin.text.toString()).matches())
        {
            Toast.makeText(this,"Email Tidak Valid", Toast.LENGTH_LONG).show()
            return
        }

        if(addPasswordValue.text.toString().length < 10)
        {
            Toast.makeText(this,"Password Minimal 10 Karakter", Toast.LENGTH_LONG).show()
            return
        }

        auth.signInWithEmailAndPassword(addEmailLogin.text.toString(), addPasswordValue.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    reload(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "${task.exception?.message}",
                        Toast.LENGTH_SHORT).show()
                    reload(null)
                }
            }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            Intent(this@MainActivity, UMKMPage::class.java).also { intent ->
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }

    fun reload(currentUser: FirebaseUser?)
    {
        if(currentUser != null)
        {
            val UMKMHome = Intent(this, UMKMPage::class.java)
            startActivity(UMKMHome)
        }
    }

}