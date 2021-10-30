package com.example.learningassistant.data.database.intefaces

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