package com.mrunal.noteapplicationmvvm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mrunal.noteapplicationmvvm.R
import com.mrunal.noteapplicationmvvm.db.entity.Note
import kotlinx.android.synthetic.main.note_item.view.*

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteHolder>() {

    private var notes: List<Note> = ArrayList()

    fun setNotes(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteHolder(itemView)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        var currentNote: Note = notes.get(position)
        holder.textViewTitle.setText(currentNote.title)
        holder.textViewDescription.setText(currentNote.description)
    }

    inner class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewTitle: TextView = itemView.text_view_title
        var textViewDescription: TextView = itemView.text_view_description
    }
}

