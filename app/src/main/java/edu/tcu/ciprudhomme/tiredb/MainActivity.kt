package edu.tcu.ciprudhomme.tiredb

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private var noteListView: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initWidgets()
        loadFromDBToMemory()
        setNoteAdapter()
        setOnClickListener()
    }


    private fun initWidgets() {
        noteListView = findViewById(R.id.noteListView)
    }

    private fun loadFromDBToMemory() {
        val sqLiteManager = SQLiteManager.instanceOfDatabase(this)
        sqLiteManager.populateNoteListArray()
    }

    private fun setNoteAdapter() {
        val noteAdapter = NoteAdapter(applicationContext, Note.nonDeletedNotes())
        noteListView!!.adapter = noteAdapter
    }


    private fun setOnClickListener() {
        noteListView!!.onItemClickListener =
            OnItemClickListener { adapterView, view, position, l ->
                val selectedNote = noteListView!!.getItemAtPosition(position) as Note
                val editNoteIntent = Intent(
                    applicationContext,
                    NoteDetailActivity::class.java
                )
                editNoteIntent.putExtra(Note.NOTE_EDIT_EXTRA, selectedNote.id)
                startActivity(editNoteIntent)
            }
    }


    fun newNote(view: View?) {
        val newNoteIntent = Intent(
            this,
            NoteDetailActivity::class.java
        )
        startActivity(newNoteIntent)
    }

    override fun onResume() {
        super.onResume()
        setNoteAdapter()
    }
}