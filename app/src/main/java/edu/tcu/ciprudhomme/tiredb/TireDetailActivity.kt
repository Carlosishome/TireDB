package edu.tcu.ciprudhomme.tiredb

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Date

class TireDetailActivity : AppCompatActivity() {
    private var tireSizeEditText: EditText? = null
    private var dotEditText: EditText? = null
    private var treadLifeEditText: EditText? = null
    private var brandEditText: EditText? = null
    private var quantityEditText: EditText? = null
    private var patchesEditText: EditText? = null
    private var locationEditText: EditText? = null
    private var deleteButton: Button? = null
    private var selectedTire: Tire? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tire_detail) // Refer to the correct layout
        initWidgets()
        checkForEditTire()
    }

    private fun initWidgets() {
        tireSizeEditText = findViewById(R.id.tireSizeEditText)
        dotEditText = findViewById(R.id.dotEditText)
        treadLifeEditText = findViewById(R.id.treadLifeEditText)
        brandEditText = findViewById(R.id.brandEditText)
        quantityEditText = findViewById(R.id.quantityEditText)
        patchesEditText = findViewById(R.id.patchesEditText)
        locationEditText = findViewById(R.id.locationEditText)
        deleteButton = findViewById(R.id.deleteTireButton)
    }

    private fun checkForEditTire() {
        val previousIntent = intent
        val passedTireID = previousIntent.getIntExtra(Tire.TIRE_EDIT_EXTRA, -1)
        selectedTire = Tire.getTireForID(passedTireID)

        if (selectedTire != null) {
            tireSizeEditText!!.setText(selectedTire!!.tireSize)
            dotEditText!!.setText(selectedTire!!.dot)
            treadLifeEditText!!.setText(selectedTire!!.treadLife.toString())
            brandEditText!!.setText(selectedTire!!.brand)
            quantityEditText!!.setText(selectedTire!!.quantity.toString())
      //      patchesEditText!!.setText(if (selectedTire!!.hasPatches) "Yes" else "No")
            locationEditText!!.setText(selectedTire!!.location)
        } else {
            deleteButton!!.visibility = View.INVISIBLE
        }
    }

    // Save the tire when the save button is clicked
    fun saveTire(view: View?) {
        val sqLiteManager = SQLiteManager.instanceOfDatabase(this)
        val tireSize = tireSizeEditText!!.text.toString()
        val dot = dotEditText!!.text.toString()
        val treadLife = treadLifeEditText!!.text.toString().toIntOrNull() ?: 0
        val brand = brandEditText!!.text.toString()
        val quantity = quantityEditText!!.text.toString().toIntOrNull() ?: 0
        val hasPatches = patchesEditText!!.text.toString().equals("Yes", ignoreCase = true)
        val location = locationEditText!!.text.toString()

        // Ensure required fields are filled
        if (tireSize.isEmpty() || dot.isEmpty()) {
            Toast.makeText(this, "Tire Size and DOT are required", Toast.LENGTH_SHORT).show()
            return
        }

        // If editing an existing tire
        if (selectedTire == null) {
            val id = Tire.tireArrayList.size
            val newTire = Tire(id, tireSize, dot, treadLife, brand, quantity, hasPatches, location)
            Tire.tireArrayList.add(newTire)
            sqLiteManager.addTireToDatabase(newTire)
        } else {
            selectedTire!!.tireSize = tireSize
            selectedTire!!.dot = dot
            selectedTire!!.treadLife = treadLife
            selectedTire!!.brand = brand
            selectedTire!!.quantity = quantity
          //  selectedTire!!.hasPatches = hasPatches
            selectedTire!!.location = location
            sqLiteManager.updateTireInDB(selectedTire)
        }

        finish()
    }

    fun deleteTire(view: View?) {
        if (selectedTire != null) {
            selectedTire!!.deleted = Date()
            val sqLiteManager = SQLiteManager.instanceOfDatabase(this)
            sqLiteManager.updateTireInDB(selectedTire)
            finish()
        }
    }
}
