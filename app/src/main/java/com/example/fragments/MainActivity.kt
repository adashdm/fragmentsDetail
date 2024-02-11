package com.example.fragments
import ListFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lists.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, ListFragment())
            .commit()
    }
}
