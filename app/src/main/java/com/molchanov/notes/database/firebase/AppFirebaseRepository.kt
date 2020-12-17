package com.molchanov.notes.database.firebase

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.molchanov.notes.database.DataBaseRepository
import com.molchanov.notes.model.AppNote
import com.molchanov.notes.utils.*

class AppFirebaseRepository : DataBaseRepository {

    init {
        //Создаем переменную для работы с Firebase Authentication SDK
        AUTH = FirebaseAuth.getInstance()
    }
    override val allNotes: LiveData<List<AppNote>> = AllNotesLiveData()

    override suspend fun insert(note: AppNote, onSuccess: () -> Unit) {
        //Получаем уникальный ключ - id конкретной заметки
        val idNote = REF_DATABASE.push().key.toString()
        //Чтобы удобно было обновлять все чайлды и элементы в ноде создаем мар
        val mapNote = hashMapOf<String, Any>()
        mapNote[ID_FIREBASE] = idNote
        mapNote[NAME] = note.name
        mapNote[TEXT] = note.text

        REF_DATABASE.child(idNote)
            .updateChildren(mapNote)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { showToast(it.message.toString()) }
    }

    override suspend fun delete(note: AppNote, onSuccess: () -> Unit) {
        REF_DATABASE.child(note.idFirebase).removeValue()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { showToast(it.message.toString()) }
    }

    //Пытаемся присоединиться к базе данных через логин и пароль
    override fun connectToDatabase(onSuccess: () -> Unit, onFail: (String) -> Unit) {

        if (AppPreference.getInitUser()) {
            initRefs()
            onSuccess()
        } else {
            //Пытаемся войти по логину и паролю
            AUTH.signInWithEmailAndPassword(EMAIL, PASSWORD)
                .addOnSuccessListener {
                    //в случае успеха инициализируем ссылки и передаем колбэк
                    initRefs()
                    onSuccess() }
                .addOnFailureListener {
                    //В случае неудачи создаем новый аккаунт с этими логином и паролем
                    // и если создается - инициализируем ссылки и передаем колбэк
                    AUTH.createUserWithEmailAndPassword(EMAIL, PASSWORD)
                        .addOnSuccessListener {
                            initRefs()
                            onSuccess }
                        .addOnFailureListener { onFail(it.message.toString()) }
                }
        }
        }



    override fun signOut() {
        AUTH.signOut()
    }
}

    private fun initRefs() {
        CURRENT_ID = AUTH.currentUser?.uid.toString()
        //Получаем ссылку на базу данных
        REF_DATABASE = FirebaseDatabase.getInstance().reference
            //Тут сообщаем, что храним заметки под уникальным id (UID) пользователя
            .child(CURRENT_ID)
    }