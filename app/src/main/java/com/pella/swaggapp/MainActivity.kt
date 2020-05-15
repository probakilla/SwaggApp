package com.pella.swaggapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val randomBtn = findViewById<Button>(R.id.goto_random_faction)
        randomBtn.setOnClickListener {
            val intent = Intent(this, RandomFaction::class.java)
            startActivity(intent)
        }
    }
}
