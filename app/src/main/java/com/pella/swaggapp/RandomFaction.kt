package com.pella.swaggapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_random_faction.*

class RandomFaction : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_faction)
        random_faction_btn.setOnClickListener {
            val factionName = getRandomFaction()
            faction_text.text = factionName
            backgroundMap(factionName)
        }
    }

    private fun getRandomFaction(): String {
        val list = listOf(
            "Eldars",
            "Tau",
            "Imperial Guard",
            "Orks",
            "Space Marines",
            "Necrons",
            "Sisters of battle",
            "Dark eldars",
            "Chaos"
        )
        return list.random()
    }

    private fun backgroundMap(factionName: String) {
        val map: Map<String, () -> Unit> = mapOf(
            "Eldars" to { faction_layout.setBackgroundResource(R.drawable.faction_eldars) },
            "Tau" to { faction_layout.setBackgroundResource(R.drawable.faction_tau) },
            "Imperial Guard" to { faction_layout.setBackgroundResource(R.drawable.faction_imperial_guard) },
            "Orks" to { faction_layout.setBackgroundResource(R.drawable.faction_orks) },
            "Space Marines" to { faction_layout.setBackgroundResource(R.drawable.faction_space_marines) },
            "Necrons" to { faction_layout.setBackgroundResource(R.drawable.faction_necrons) },
            "Sisters of battle" to { faction_layout.setBackgroundResource(R.drawable.faction_sisters) },
            "Dark eldars" to { faction_layout.setBackgroundResource(R.drawable.faction_dark_eldar) },
            "Chaos" to { faction_layout.setBackgroundResource(R.drawable.faction_chaos) }
        )
        map[factionName]?.invoke()
    }
}
