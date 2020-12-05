package com.poc.openweatherapisample.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.poc.openweatherapisample.R
import com.shivtechs.maplocationpicker.MapUtility
import kotlinx.android.synthetic.main.main_activity.*


class MainActivity : AppCompatActivity() {
    private lateinit var navHostFragment: NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        MapUtility.apiKey = getResources().getString(R.string.your_api_key);
        setSupportActionBar(toolbar)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navHostFragment.navController.addOnDestinationChangedListener { _, destination, bundle ->
            toolbar.title = destination.label
            when (destination.id) {
                R.id.mainFragment -> {
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
                else -> supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                navHostFragment.navController.popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
