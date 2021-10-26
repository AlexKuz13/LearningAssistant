package com.example.learningassistant.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var id: String = "",
    var fullName: String = "Name Surname",
    var email: String = "",
    var password: String = "",
    var status: String = "",
    var info: String = "Не задано",
    var rating: Double = 0.0,
    var completeWorks: Int = 0,
    var photoUrl: String = "empty",
    var rating_sum: Double = 0.0
) : Parcelable