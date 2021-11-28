package com.example.learningassistant.ui.fragments.main

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.example.learningassistant.data.DataStoreRepository
import com.example.learningassistant.data.SubjectAndClass
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
    private val dataStoreRepository: DataStoreRepository,
    appFirebaseUser: AppFirebaseUser,
    private val appFirebaseTask: AppFirebaseTask,
    application: Application
) : AndroidViewModel(application) {

    private lateinit var subjectAndClass: SubjectAndClass

    var readSubjectAndClass = dataStoreRepository.readSubjectAndClass


    val currentUser: LiveData<User> = appFirebaseUser.currentUser


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


    // var tasksData: MutableLiveData<NetworkResult<List<Task>?>> = MutableLiveData()

    fun listTasks(filter: List<String>): LiveData<List<Task>> {
        return appFirebaseTask.allTasks(listOf(filter[0], filter[1]))
    }

//    fun getTasks(filter: List<String>) = viewModelScope.launch {
//        getTasksSafeCall(filter)
//    }

//    private fun getTasksSafeCall(filter: List<String>) {
//        tasksData.value = NetworkResult.Loading()
//        if (hasInternetConnection()){
//            try {
//                val data = appFirebaseTask.allTasks(listOf(filter[0], filter[1]))
//                tasksData.value = handleTasksData(data)
//            } catch (e:Exception){
//                tasksData.value = NetworkResult.Error("Tasks not found ")
//            }
//        } else{
//            tasksData.value = NetworkResult.Error("No Internet Connection")
//        }
//    }
//
//    private fun handleTasksData(data: LiveData<List<Task>>): NetworkResult<List<Task>?> {
//        return when {
//            data.value.isNullOrEmpty() -> NetworkResult.Error("Tasks not ")
//            else ->
//               NetworkResult.Success(data.value)
//        }
//    }


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


    private fun hasInternetConnection(): Boolean {
        val connectivityManager =
            getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }


}