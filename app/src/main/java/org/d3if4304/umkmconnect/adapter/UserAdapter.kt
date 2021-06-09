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
import de.hdodenhof.circleimageview.CircleImageView
import org.d3if4304.umkmconnect.R
import org.d3if4304.umkmconnect.model.User
import org.d3if4304.umkmconnect.menupage.ChatProfile


class UserAdapter(private val context: Context,private val userList:ArrayList<User>) : RecyclerView.Adapter<UserAdapter.ViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_row,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]
        holder.txtFullName.text = user.UserName
        Glide.with(context).load(user.userImage).placeholder(R.drawable.ic_account_circle_profile).into(holder.ImgUser)

        holder.touchChat.setOnClickListener()
        {
            val startChat = Intent(context,ChatProfile::class.java)

            startChat.putExtra("userID",user.userID)
            context.startActivity(startChat)
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val txtFullName:TextView = view.findViewById(R.id.full_Name)
        val txtTemp:TextView = view.findViewById(R.id.chat_temp)
        val ImgUser:CircleImageView = view.findViewById(R.id.prof_Image)
        val touchChat:LinearLayout = view.findViewById(R.id.touch_Chat)
    }
}