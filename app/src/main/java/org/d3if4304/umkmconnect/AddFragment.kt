package org.d3if4304.umkmconnect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add_fragment.*
import kotlinx.android.synthetic.main.activity_registration_page.*

class AddFragment : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var dataBaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_fragment)

        auth = Firebase.auth

        tambah.setOnClickListener()
        {
            val Kedai = inputNama.text.toString()
            val Alamat = inputAlamat.text.toString()
            val Deskripsi = inputDeskripsi.text.toString()

            if(Kedai.isEmpty()  && Alamat.isEmpty() && Deskripsi.isEmpty())
            {
                Toast.makeText(this,"Input Tidak Boleh Kosong", Toast.LENGTH_LONG).show()
            }
                addNewTrader(Kedai,Alamat,Deskripsi)
                Toast.makeText(this,"Work", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addNewTrader(kedai:String, alamat:String, deskripsi:String)
    {

        // Add New Trader
        val reference: DatabaseReference = FirebaseDatabase.getInstance().reference
        val hasMap:HashMap<String,String> = HashMap()

        hasMap.put("nameTrader",kedai)
        hasMap.put("addressTrader",alamat)
        hasMap.put("descTrader",deskripsi)

        reference.child("TraderList").push().setValue(hasMap)

    }

}