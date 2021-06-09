package org.d3if4304.umkmconnect.menupage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat_page.view.*
import kotlinx.android.synthetic.main.activity_chat_profile.*
import kotlinx.android.synthetic.main.activity_profile_page.*
import kotlinx.android.synthetic.main.chat_row.*
import org.d3if4304.umkmconnect.R
import org.d3if4304.umkmconnect.UMKMPage
import org.d3if4304.umkmconnect.adapter.ChatAdapter
import org.d3if4304.umkmconnect.adapter.UserAdapter
import org.d3if4304.umkmconnect.model.Chat
import org.d3if4304.umkmconnect.model.User

class ChatProfile : AppCompatActivity() {

    var firebaseUser: FirebaseUser? = null
    var reference: DatabaseReference? = null
    var chatList = ArrayList<Chat>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_profile)

        chatRecylcerView.layoutManager =LinearLayoutManager(this,LinearLayout.VERTICAL,false)

        var Intent = intent
        var userID = Intent.getStringExtra("userID")

        back_Chat.setOnClickListener()
        {
            val BackToMainMenu = Intent(this, UMKMPage::class.java)
            startActivity(BackToMainMenu)
        }

        firebaseUser = FirebaseAuth.getInstance().currentUser
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userID!!)

        reference!!.addValueEventListener(object : ValueEventListener{

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                username_Chat.text = user!!.UserName
                if(user.userImage == "")
                {
                    image_Chat.setImageResource(R.drawable.ic_account_circle)
                } else {
                    Glide.with(this@ChatProfile).load(user.userImage).into(image_Chat)
                }
            }
        })

        sendBtn.setOnClickListener()
        {
            var message:String = addChatText.text.toString()
            if(message.isEmpty())
            {
                Toast.makeText(this,"Message is Empty",Toast.LENGTH_SHORT).show()
                addChatText.setText("")
            } else {
                sendMessage(firebaseUser!!.uid, userID,message)
                addChatText.setText("")
            }
        }

        readMessage(firebaseUser!!.uid, userID)

    }

    private fun sendMessage(senderID:String, receiverID:String, message:String)
    {
        var reference: DatabaseReference? = FirebaseDatabase.getInstance().reference
        var hasMap:HashMap<String,String> = HashMap()
        hasMap.put("senderID",senderID)
        hasMap.put("receiverID",receiverID)
        hasMap.put("message",message)

        reference!!.child("Chat").push().setValue(hasMap)
    }

    fun readMessage(senderID:String, receiverID:String)
    {
        val databaseReference:DatabaseReference = FirebaseDatabase.getInstance().getReference("Chat")

        databaseReference.addValueEventListener(object : ValueEventListener{

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                chatList.clear()
                for (dataSnapShot: DataSnapshot in snapshot.children)
                {
                    val chat = dataSnapShot.getValue(Chat::class.java)
                    if(chat!!.senderID.equals(senderID) && chat!!.receiverID.equals(receiverID) || chat!!.senderID.equals(receiverID) && chat!!.receiverID.equals(senderID)) {
                        chatList.add(chat)
                    }
                }

                val chatAdapter = ChatAdapter(this@ChatProfile, chatList)
                chatRecylcerView.adapter = chatAdapter

            }
        })
    }
}