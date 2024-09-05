package edu.tcu.ciprudhomme.tiredb

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var tireListView: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initWidgets()
        loadFromDBToMemory()
        setTireAdapter()
        setOnClickListener()
    }

    private fun initWidgets() {
        tireListView = findViewById(R.id.noteListView)
    }

    private fun loadFromDBToMemory() {
        val sqLiteManager = SQLiteManager.instanceOfDatabase(this)
        sqLiteManager.populateTireListArray()
    }

    private fun setTireAdapter() {
        val tireAdapter = TireAdapter(
            applicationContext,
            Tire.nonDeletedTires()
        )
        tireListView!!.adapter = tireAdapter
    }

    private fun setOnClickListener() {
        tireListView!!.onItemClickListener =
            OnItemClickListener { adapterView, view, position, l ->
                val selectedTire = tireListView!!.getItemAtPosition(position) as Tire
                val editTireIntent = Intent(
                    applicationContext,
                    TireDetailActivity::class.java
                )
                editTireIntent.putExtra(Tire.TIRE_EDIT_EXTRA, selectedTire.id)
                startActivity(editTireIntent)
            }
    }

    fun newTire(view: View?) {
        val newTireIntent = Intent(
            this,
            TireDetailActivity::class.java
        )
        startActivity(newTireIntent)
    }

    override fun onResume() {
        super.onResume()
        setTireAdapter()
    }
}
