package com.example.learningassistant.models

data class Message(
    var from:String="",
    var text:String="",
    var imageUrl:String="empty",
    var type_mes:String="",
    var timeStamp: Any?=""
)