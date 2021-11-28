package com.example.learningassistant.data.database

import android.annotation.SuppressLint
import com.example.learningassistant.data.database.intefaces.DatabaseChatRepository
import com.example.learningassistant.data.database.intefaces.DatabaseMessageRepository
import com.example.learningassistant.data.database.intefaces.DatabaseStorage
import com.example.learningassistant.models.Chat
import com.example.learningassistant.models.Message
import com.example.learningassistant.models.Rating
import com.example.learningassistant.models.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference

//lateinit var AUTH: FirebaseAuth
@SuppressLint("StaticFieldLeak")
lateinit var DB: FirebaseFirestore
lateinit var USER: User
//lateinit var TASK: Task
lateinit var MESSAGE: Message
lateinit var RATING: Rating
lateinit var CHAT: Chat
lateinit var UID: String
lateinit var REF_STORAGE_ROOT: StorageReference

//lateinit var REPOSITORY:DatabaseRepository
//lateinit var USER_REPOSITORY: DatabaseUserRepository
//lateinit var TASK_REPOSITORY: DatabaseTaskRepository
lateinit var CHAT_REPOSITORY: DatabaseChatRepository
lateinit var MESSAGE_REPOSITORY: DatabaseMessageRepository
lateinit var STORAGE:DatabaseStorage

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

const val TASK_SCHOOL_SUBJECT = "school_subject"
const val TASK_SCHOOL_CLASS = "school_class"
const val TASK_FROM = "from"
