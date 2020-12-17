package com.molchanov.notes.screens.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.molchanov.notes.R
import com.molchanov.notes.model.AppNote

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainHolder>() {

    private var mListNotes = emptyList<AppNote>()
    class MainHolder (view : View) : RecyclerView.ViewHolder (view) {
        val nameNote : TextView = view.findViewById(R.id.tv_item_note_name)
        val textNote : TextView = view.findViewById(R.id.tv_item_note_text)
    }

    //функция отрабатывает, когда холдер появляется на экране
    override fun onViewAttachedToWindow(holder: MainHolder) {
        //подключаем слушатель нажатия
        holder.itemView.setOnClickListener {
            //при нажатии передаем в функцию клик елемент апп ноут по актуальной позиции холдера
            MainFragment.click(mListNotes[holder.adapterPosition])
        }
        super.onViewAttachedToWindow(holder)
    }

    //функция отрабатывает, когда холдер исчезает с экрана
    override fun onViewDetachedFromWindow(holder: MainHolder) {
        //отключаем слушатель нажатия
        holder.itemView.setOnClickListener(null)
        super.onViewDetachedFromWindow(holder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return MainHolder(view)
    }

    override fun getItemCount(): Int = mListNotes.size

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.textNote.text = mListNotes[position].text
        holder.nameNote.text = mListNotes[position].name
    }



    fun setList(list: List<AppNote>) {
        mListNotes = list
        notifyDataSetChanged()
    }
}