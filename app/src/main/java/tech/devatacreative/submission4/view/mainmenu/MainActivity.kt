package tech.devatacreative.submission4.view.mainmenu

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import tech.devatacreative.submission4.R

class MainActivity : AppCompatActivity() {

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener{ item ->
        when (item.itemId){
            R.id.navigation_last_match -> {
                showFragment("lastEvent")
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_next_match -> {
                showFragment("nextEvent")
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorite -> {
                showFragment("favorite")
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showFragment("lastEvent")
        navigation.setOnNavigationItemSelectedListener (onNavigationItemSelectedListener)
    }
    private fun showFragment(tag : String?) {

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.contentFragment, MainActivityFragment.newInstance(), tag)
                .commit()
    }
}
