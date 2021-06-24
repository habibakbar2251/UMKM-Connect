package org.d3if4304.umkmconnect.menupage

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home_page.view.*
import kotlinx.android.synthetic.main.activity_search_page.*
import kotlinx.android.synthetic.main.activity_search_page.view.*
import org.d3if4304.umkmconnect.R
import org.d3if4304.umkmconnect.adapter.TraderAdapter
import org.d3if4304.umkmconnect.model.Trader


class SearchPage : Fragment(){

    private var recyclerView: RecyclerView? = null
    private var searchEditText: EditText? = null
    lateinit var auth: FirebaseAuth
    var searchList = ArrayList<Trader>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_search_page, container, false)

        // View All Trader
        recyclerView = view.findViewById(R.id.search_Trader)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        searchEditText = view.findViewById(R.id.searchBar)

        // View Recycler View
        viewTrader()

        // Search Function
        searchEditText?.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(cs: CharSequence?, start: Int, before: Int, count: Int) {
                searchTrader(cs.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        return view
    }

    private fun viewTrader() {
        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("TraderList")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                searchList.clear()
                if (snapshot.exists()) {
                    for (dataSnapShot: DataSnapshot in snapshot.children) {
                        val trader = dataSnapShot.getValue(Trader::class.java)
                        searchList.add(trader!!)
                    }


                    val traderAdapter = TraderAdapter(requireContext(), searchList)
                    val traderView = view?.search_Trader
                    traderView?.adapter = traderAdapter
                    traderView?.layoutManager = LinearLayoutManager(requireContext())

                }
            }
        })
    }

    private fun searchTrader(str:String) {
        val databaseReference: Query = FirebaseDatabase.getInstance().getReference("TraderList").orderByChild("nameTrader").startAt(str).endAt(str + "\uf8ff")

        databaseReference.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                searchList.clear()
                if (snapshot.exists()) {
                    for (dataSnapShot: DataSnapshot in snapshot.children) {
                        val trader = dataSnapShot.getValue(Trader::class.java)
                        searchList.add(trader!!)
                    }


                    val traderAdapter = TraderAdapter(requireContext(), searchList)
                    val traderView = view?.search_Trader
                    traderView?.adapter = traderAdapter
                    traderView?.layoutManager = LinearLayoutManager(requireContext())

                }
            }
        })
    }

}
