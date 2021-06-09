package org.d3if4304.umkmconnect.menupage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat_page.view.*
import kotlinx.android.synthetic.main.activity_home_page.*
import kotlinx.android.synthetic.main.activity_home_page.view.*
import org.d3if4304.umkmconnect.R
import org.d3if4304.umkmconnect.adapter.TraderAdapter
import org.d3if4304.umkmconnect.adapter.UserAdapter
import org.d3if4304.umkmconnect.model.Trader
import org.d3if4304.umkmconnect.model.User

class HomePage : Fragment() {

    lateinit var auth: FirebaseAuth
    var traderList = ArrayList<Trader>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_home_page,container,false)


        getAllTraderData()

        return view
    }

    private fun getAllTraderData()
    {
        val databaseReference: DatabaseReference =FirebaseDatabase.getInstance().getReference("TraderList")

        databaseReference.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(),error.message, Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                traderList.clear()
                if(snapshot.exists())
                {
                    for(dataSnapShot: DataSnapshot in snapshot.children) {
                        val trader = dataSnapShot.getValue(Trader::class.java)
                            traderList.add(trader!!)
                    }
                    val traderAdapter = TraderAdapter(requireContext(),traderList)
                    val traderView = view?.recyclerTrader
                    traderView?.adapter = traderAdapter
                    traderView?.layoutManager = LinearLayoutManager(requireContext())
                }
            }

        })
    }
}
