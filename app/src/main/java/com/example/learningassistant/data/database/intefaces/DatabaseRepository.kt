package com.example.learningassistant.data.database.intefaces

import dagger.hilt.android.scopes.ViewModelScoped

@ViewModelScoped
interface DatabaseRepository {

    fun connectToDatabase(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFail: (String) -> Unit
    )

    fun createDatabase(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFail: (String) -> Unit
    )

    fun signOut()

}