package org.d3if4304.umkmconnect.menupage

import android.os.Bundle
import android.view.*
import android.widget.Toast

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home_page.view.*
import kotlinx.android.synthetic.main.activity_search_page.*
import org.d3if4304.umkmconnect.R
import org.d3if4304.umkmconnect.adapter.TraderAdapter
import org.d3if4304.umkmconnect.model.Trader



class SearchPage : Fragment(){

    lateinit var auth: FirebaseAuth
    var searchList = ArrayList<Trader>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_search_page,container,false)

        getAllTraderData()

        return view
    }

    private fun getAllTraderData() {
    }
}