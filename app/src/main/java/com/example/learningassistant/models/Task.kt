package com.example.learningassistant.models

import com.google.firebase.firestore.FieldValue

data class Task(
    var from:String="",
    var topic:String="",
    var description:String="",
    var type_des:String="",
    var timeStamp: Any?=""
)