package com.example.learningassistant.models

import java.io.Serializable

data class User (
    var id:String="",
    var fullName:String="Name Surname",
    var phone:String="",
    var status:String="",
    var info:String="Не задано",
    var rating:Double=0.0,
    var completeWorks:Int=0,
    var photoUrl:String="empty"
        ) :Serializable