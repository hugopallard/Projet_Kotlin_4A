package com.example.kotlinv2

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.example.kotlinv2.fragments.FavoritesFragment
import com.example.kotlinv2.fragments.HomeFragment
import com.example.kotlinv2.fragments.ScanQRCodeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private val homeFragment = HomeFragment()
    private val scanQRCodeFragment = ScanQRCodeFragment()
    private val favoritesFragment = FavoritesFragment()
    private lateinit var bottomNavigation: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setting the default fragment
        replaceFragment(homeFragment)

        setSupportActionBar(findViewById(R.id.toolBarDefault))
        findViewById<ImageView>(R.id.goBackButton).visibility = View.GONE

        bottomNavigation = findViewById(R.id.bottom_navigation)

        bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homePage -> replaceFragment(homeFragment)
                R.id.scanQRCodePage -> replaceFragment(scanQRCodeFragment)
                R.id.favoritesPage -> replaceFragment(favoritesFragment)
            }
            true
        }


    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }
}