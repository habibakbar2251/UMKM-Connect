package org.d3if4304.umkmconnect.menupage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat_profile.*
import kotlinx.android.synthetic.main.activity_chat_profile.back_Chat
import kotlinx.android.synthetic.main.activity_home_detail.*
import org.d3if4304.umkmconnect.R
import org.d3if4304.umkmconnect.UMKMPage
import org.d3if4304.umkmconnect.model.Trader
import org.d3if4304.umkmconnect.model.User

class HomeDetail : AppCompatActivity() {

    var reference: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_detail)

        val traderIntent = getIntent()
        val traderName = traderIntent.getStringExtra("nameTrader")
        val traderAddress = traderIntent.getStringExtra("addressTrader")
        val traderDesc = traderIntent.getStringExtra("descTrader")

        detailTraderName.text = traderName.toString()
        detailTraderAddress.text = traderAddress.toString()
        detailTraderDesc.text = traderDesc.toString()
    }
}