package org.d3if4304.umkmconnect.menupage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.activity_location_page.*
import org.d3if4304.umkmconnect.R

class LocationPage : Fragment() {

    lateinit var mapFragment: SupportMapFragment

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_location_page,container,false)


        return view
    }
}