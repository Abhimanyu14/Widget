package com.appz.abhi.widget.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.appz.abhi.widget.R
import com.appz.abhi.widget.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {


    // Data binding
    private lateinit var mainActivityBinding: MainActivityBinding


    // On activity creation starting
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set activity layout
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.main_activity)
    }
}
