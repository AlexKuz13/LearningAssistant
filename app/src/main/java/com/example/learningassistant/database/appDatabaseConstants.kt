package com.example.learningassistant.database

import com.example.learningassistant.models.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference

lateinit var AUTH: FirebaseAuth
lateinit var DB: FirebaseFirestore
lateinit var USER: User
lateinit var TASK: Task
lateinit var MESSAGE: Message
lateinit var RATING: Rating
lateinit var CHAT: Chat
lateinit var UID: String
lateinit var REF_STORAGE_ROOT: StorageReference

const val COLL_USERS = "users"
const val COLL_TASKS = "tasks"
const val COLL_MESSAGES = "messages"
const val COLL_RATINGS = "ratings"
const val COLL_CHATS_ROSTER="chats_roster"

const val FOLDER_PROFILE_IMAGE = "profile_image"

const val TYPE_TEXT = "text"
const val TYPE_IMAGE = "image"


const val CHILD_FULLNAME = "fullName"
const val CHIlD_INFO = "info"
const val CHILD_PHOTO_URL = "photoUrl"
const val CHILD_STATUS = "status"
const val CHILD_RATING = "rating"
const val CHILD_COMPLETEWORKS = "completeWorks"
const val CHILD_TIMESTAMP = "timeStamp"
