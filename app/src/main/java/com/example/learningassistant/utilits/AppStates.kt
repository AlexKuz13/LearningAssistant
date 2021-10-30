package com.example.learningassistant.utilits

import com.example.learningassistant.data.database.*

enum class AppStates(val status: String) {
    ONLINE("В сети"),
    OFFLINE("Не в сети");

    companion object {
        fun updateState(appStates: AppStates) {
            if (AUTH.currentUser != null) {
                DB.collection(COLL_USERS).document(UID)
                    .update(CHILD_STATUS, appStates.status)
                    .addOnSuccessListener { USER.status = appStates.status }
                    .addOnFailureListener { showToast(it.message.toString()) }
            }
        }
    }
}

