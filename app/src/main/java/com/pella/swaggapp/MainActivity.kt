package com.pella.swaggapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        goto_random_faction.setOnClickListener {
            startActivity(Intent(this, RandomFaction::class.java))
        }
        goto_one_piece_btn.setOnClickListener {
            startActivity(Intent(this, ScansManager::class.java))
        }
    }
}
