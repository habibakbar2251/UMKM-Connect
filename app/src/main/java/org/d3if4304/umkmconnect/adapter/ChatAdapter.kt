package org.d3if4304.umkmconnect.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import de.hdodenhof.circleimageview.CircleImageView
import org.d3if4304.umkmconnect.R
import org.d3if4304.umkmconnect.model.User
import org.d3if4304.umkmconnect.menupage.ChatProfile
import org.d3if4304.umkmconnect.model.Chat


class ChatAdapter(private val context: Context, private val chatList:ArrayList<Chat>) : RecyclerView.Adapter<ChatAdapter.ViewHolder>()
{

    private val MESSAGE_TYPE_LEFT = 0
    private val MESSAGE_TYPE_RIGHT = 1
    var firebaseUser:FirebaseUser? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == MESSAGE_TYPE_RIGHT) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.chat_row_right, parent, false)
            return ViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.chat_row_left, parent, false)
            return ViewHolder(view)
        }

    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val chat = chatList[position]
        holder.txtFullName.text = chat.message
        // Glide.with(context).load(user.userImage).placeholder(R.drawable.ic_account_circle_profile).into(holder.ImgUser)


    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val txtFullName:TextView = view.findViewById(R.id.temp_Chat)
        val ImgUser:CircleImageView = view.findViewById(R.id.prof_Image)
    }

    override fun getItemViewType(position: Int): Int {
        firebaseUser = FirebaseAuth.getInstance().currentUser
        if(chatList[position].senderID == firebaseUser!!.uid)
        {
            return MESSAGE_TYPE_RIGHT
        } else {
            return MESSAGE_TYPE_LEFT
        }
    }

}