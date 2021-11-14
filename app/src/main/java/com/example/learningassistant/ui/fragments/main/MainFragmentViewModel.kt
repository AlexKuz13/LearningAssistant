package com.example.learningassistant.ui.fragments.main

import androidx.lifecycle.*
import com.example.learningassistant.data.DataStoreRepository
import com.example.learningassistant.data.SubjectAndClass
import com.example.learningassistant.data.database.TASK_REPOSITORY
import com.example.learningassistant.data.database.USER_REPOSITORY
import com.example.learningassistant.data.database.firebase.AppFirebaseTask
import com.example.learningassistant.data.database.firebase.AppFirebaseUser
import com.example.learningassistant.models.Task
import com.example.learningassistant.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private lateinit var subjectAndClass: SubjectAndClass

    var readSubjectAndClass = dataStoreRepository.readSubjectAndClass


    val currentUser: LiveData<User>


    init {
        USER_REPOSITORY = AppFirebaseUser() // через Hilt внедрить попробовать
        TASK_REPOSITORY = AppFirebaseTask()
        currentUser = USER_REPOSITORY.currentUser
    }


    fun saveSubjectAndClass() =
        viewModelScope.launch(Dispatchers.IO) {
            if (this@MainFragmentViewModel::subjectAndClass.isInitialized) {
                dataStoreRepository.saveSubjectAndClass(
                    subjectAndClass.schoolSubject,
                    subjectAndClass.schoolSubjectId,
                    subjectAndClass.schoolClass,
                    subjectAndClass.schoolClassId
                )
            }
        }


    fun listTasks(filter: List<String>): LiveData<List<Task>> {
        return AppFirebaseTask().allTasks(listOf(filter[0], filter[1]))
    }


    fun saveSubjectAndClassTemp(
        schoolSubject: String,
        schoolSubjectId: Int,
        schoolClass: String,
        schoolClassId: Int
    ) {
        subjectAndClass = SubjectAndClass(
            schoolSubject,
            schoolSubjectId,
            schoolClass,
            schoolClassId
        )
    }


}