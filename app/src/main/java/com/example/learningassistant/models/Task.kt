package com.example.learningassistant.models


data class Task(
    var from: String = "",
    var school_subject: String = "",
    var school_class: String = "",
    var description: String = "",
    var type_des: String = "",
    var timeStamp: Any? = ""
)