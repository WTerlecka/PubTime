package com.example.pubtime

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.ListView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var seekBar: SeekBar
    private lateinit var textView: TextView
    private lateinit var checkBoxPilkaNozna: CheckBox
    private lateinit var checkBoxTennis: CheckBox
    private lateinit var checkBoxZuzel: CheckBox
    private lateinit var button: Button
    private lateinit var listView: ListView

    private val pubs = mapOf(
        5 to listOf(
            "pub superBowl - mecze piłkarskie",
            "pub superRakieta - mecze tenisa",
            "pub JackRussel - mecze piłkarskie"
        ),
        10 to listOf(
            "pub superBowl - mecze piłkarskie",
            "pub superRakieta - mecze tenisa",
            "pub JackRussel - mecze piłkarskie",
            "pub footballGame - mecze piłkarskie",
            "pub Racing - zawody żużlowe",
            "pub biforek - mecze tenisowe"
        ),
        15 to listOf(
            "pub superBowl - mecze piłkarskie",
            "pub superRakieta - mecze tenisa",
            "pub JackRussel - mecze piłkarskie",
            "pub footballGame - mecze piłkarskie",
            "pub Racing - zawody żużlowe",
            "pub biforek - mecze tenisowe",
            "pub ekstraGame - mecze piłkarskie, mecze tenisa, zawody żużlowe",
            "pub Wilki, mecze piłkarskie"
        ),
        20 to listOf(
            "pub superBowl - mecze piłkarskie",
            "pub superRakieta - mecze tenisa",
            "pub JackRussel - mecze piłkarskie",
            "pub footballGame - mecze piłkarskie",
            "pub Racing - zawody żużlowe",
            "pub biforek - mecze tenisowe",
            "pub ekstraGame - mecze piłkarskie, mecze tenisa, zawody żużlowe",
            "pub Wilki, mecze piłkarskie",
            "pub dzikie Koty, mecze piłkarskie",
            "pub czarno na białym - mecze tenisa"
        )
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seekBar = findViewById(R.id.seekBar)
        textView = findViewById(R.id.textView)
        checkBoxPilkaNozna = findViewById(R.id.checkBoxPilka)
        checkBoxTennis = findViewById(R.id.checkBoxTenis)
        checkBoxZuzel = findViewById(R.id.checkBoxZuzel)
        button = findViewById(R.id.button)
        listView = findViewById(R.id.listView)

        textView.text = "0 km"
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val odleglosc = (progress / 2) * 5
                textView.text = "$odleglosc km"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        button.setOnClickListener {
            val odleglosc = (seekBar.progress / 2) * 5
            val wybranePuby = getFilteredPubs(odleglosc)
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, wybranePuby)
            listView.adapter = adapter
        }
    }

    private fun getFilteredPubs(distance: Int): List<String> {
        val puby = pubs[distance] ?: emptyList()

        val filteredByCheckbox = puby.filter { pub ->
            when {
                checkBoxPilkaNozna.isChecked && checkBoxTennis.isChecked && checkBoxZuzel.isChecked -> true
                checkBoxPilkaNozna.isChecked && pub.contains("mecze piłkarskie") -> true
                checkBoxTennis.isChecked && pub.contains("mecze tenisa") -> true
                checkBoxZuzel.isChecked && pub.contains("zawody żużlowe") -> true
                else -> false
            }
        }

        return if (filteredByCheckbox.isEmpty()) {
            listOf("Brak barów w zadanej odległości :(")
        } else {
            filteredByCheckbox
        }
    }
}