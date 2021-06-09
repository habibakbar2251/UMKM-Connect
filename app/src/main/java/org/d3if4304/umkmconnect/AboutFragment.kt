package org.d3if4304.umkmconnect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_about_fragment.*

class AboutFragment : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_fragment)

        setSupportActionBar(backToolbar)
        backToolbar.setNavigationOnClickListener {
            val BackToMainMenu = Intent(this,UMKMPage::class.java)
            startActivity(BackToMainMenu)
        }
    }

}