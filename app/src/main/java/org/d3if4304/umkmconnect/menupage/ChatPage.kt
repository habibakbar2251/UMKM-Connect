package org.d3if4304.umkmconnect.menupage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat_page.view.*
import org.d3if4304.umkmconnect.R
import org.d3if4304.umkmconnect.model.User
import org.d3if4304.umkmconnect.adapter.UserAdapter

class ChatPage : Fragment() {

    lateinit var auth: FirebaseAuth
    var userList = ArrayList<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_chat_page,container,false)

        getUserContact()

        return view
    }

    fun getUserContact()
    {
        val firebase: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
        val databaseReference: DatabaseReference =FirebaseDatabase.getInstance().getReference("Users")

        databaseReference.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(),error.message,Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (dataSnapShot: DataSnapshot in snapshot.children)
                {
                    val user = dataSnapShot.getValue(User::class.java)
                    if(!user!!.userID.equals(firebase.uid))
                    {
                        userList.add(user)
                    }
                }
                val userAdapter = UserAdapter(requireContext(),userList)
                val chatView = view?.chat_list
                chatView?.adapter = userAdapter
                chatView?.layoutManager = LinearLayoutManager(requireContext())
            }
        })
    }

}