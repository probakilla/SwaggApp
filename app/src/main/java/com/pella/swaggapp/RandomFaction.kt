package com.pella.swaggapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_random_faction.*

class RandomFaction : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_faction)
        random_faction_btn.setOnClickListener {
            val factionName = randomFaction()
            faction_text.text = factionName
            backgroundMap(factionName)
        }
    }

    private external fun randomFaction(): String

    companion object {
        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
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
