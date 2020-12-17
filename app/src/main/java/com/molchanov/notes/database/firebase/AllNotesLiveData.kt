package com.molchanov.notes.database.firebase

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.molchanov.notes.model.AppNote
import com.molchanov.notes.utils.REF_DATABASE

class AllNotesLiveData : LiveData<List<AppNote>> () {


    //Функция срабатывает при изменениях в базе данных (добавление/удаление заметки)
    private val listener = object : ValueEventListener {

        //В этой функции получаем лайвдату в виде мапы
        override fun onDataChange(p0: DataSnapshot) {
            value = p0.children.map {
                it.getValue(AppNote::class.java)?: AppNote()
            }
        }

        override fun onCancelled(p0: DatabaseError) {

        }

    }

    //Работает, если лайвдата активна - вьюмодел активна - мейн фрагмент активен
    //То есть если мы сворачиваем приложение или переходим в другой фрагмент слушатель
    //будет отключаться. Защита от утечки памяти.
    override fun onActive() {
        REF_DATABASE.addValueEventListener(listener)
        super.onActive()
    }

    override fun onInactive() {
        REF_DATABASE.removeEventListener(listener)
        super.onInactive()
    }
}