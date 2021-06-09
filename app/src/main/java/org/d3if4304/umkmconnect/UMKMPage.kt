package org.d3if4304.umkmconnect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile_page.*
import kotlinx.android.synthetic.main.activity_u_m_k_m_page.*
import kotlinx.android.synthetic.main.side_header.*
import org.d3if4304.umkmconnect.databinding.ActivityUMKMPageBinding
import org.d3if4304.umkmconnect.menupage.*

class UMKMPage : AppCompatActivity() {

    lateinit var toogle: ActionBarDrawerToggle
    private lateinit var auth: FirebaseAuth
    lateinit var binding: ActivityUMKMPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        val user = auth.currentUser

        setContentView(R.layout.activity_u_m_k_m_page)
        navigationView.background = null

        setSupportActionBar(mainToolbar)

        mainToolbar.setNavigationOnClickListener()
        {
            toogle = ActionBarDrawerToggle(this, drawer_menu, mainToolbar, R.string.drawer_open, R.string.drawer_close)
            toogle.syncState()
        }

        mainToolbar.setTitle(user?.displayName)
        mainToolbar.setSubtitle(user?.email)

        val homePage = HomePage()
        val searchPage = SearchPage()
        val chatPage = ChatPage()
        val profilePage = ProfilePage()

        setViewPage(homePage)
        navigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {

                R.id.mi_home -> setViewPage(homePage)
                R.id.mi_search -> setViewPage(searchPage)
                R.id.mi_chat -> setViewPage(chatPage)
                R.id.mi_profile -> setViewPage(profilePage)
            }
            true
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        nav_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> setViewPage(homePage)
                R.id.nav_search -> setViewPage(searchPage)
                R.id.nav_chat -> setViewPage(chatPage)
                R.id.nav_profile -> setViewPage(profilePage)
                R.id.nav_about -> startActivity(Intent(this, AboutFragment::class.java))
                R.id.nav_logout -> logout()
            }
            true
        }
    }

    override fun onBackPressed() {
        if (drawer_menu.isDrawerOpen(GravityCompat.START)) {

            drawer_menu.closeDrawer(GravityCompat.START)

        } else {
            super.onBackPressed()
        }
    }

    private fun setViewPage(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentView, fragment)
            commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_nav_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toogle.onOptionsItemSelected(item)) {
            return true
        }

        var itemSelected = item.itemId
        when (itemSelected) {


        }
        return false
    }

    private fun logout() {
        auth.signOut()
        Intent(this@UMKMPage, MainActivity::class.java).also { intent ->
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}