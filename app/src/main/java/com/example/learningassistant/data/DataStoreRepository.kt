package com.example.learningassistant.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.learningassistant.utilits.*
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


private val Context.dataStore by preferencesDataStore(name = PREFERENCES_NAME)

@ViewModelScoped
class DataStoreRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private object PreferenceKeys {
        val schoolSubject = stringPreferencesKey(PREFERENCES_SCHOOL_SUBJECT)
        val schoolSubjectId = intPreferencesKey(PREFERENCES_SCHOOL_SUBJECT_ID)

        val schoolClass = stringPreferencesKey(PREFERENCES_SCHOOL_CLASS)
        val schoolClassId = intPreferencesKey(PREFERENCES_SCHOOL_CLASS_ID)

    }

    private val dataStore: DataStore<Preferences> = context.dataStore

    suspend fun saveSubjectAndClass(
        schoolSubject: String,
        schoolSubjectId: Int,
        schoolClass: String,
        schoolClassId: Int
    ) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.schoolSubject] = schoolSubject
            preferences[PreferenceKeys.schoolSubjectId] = schoolSubjectId
            preferences[PreferenceKeys.schoolClass] = schoolClass
            preferences[PreferenceKeys.schoolClassId] = schoolClassId
        }
    }


    val readSubjectAndClass: Flow<SubjectAndClass> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val schoolSubject = preferences[PreferenceKeys.schoolSubject] ?: DEFAULT_SCHOOL_SUBJECT
            val schoolSubjectId = preferences[PreferenceKeys.schoolSubjectId] ?: 0
            val schoolClass = preferences[PreferenceKeys.schoolClass] ?: DEFAULT_SCHOOL_CLASS
            val schoolClassId = preferences[PreferenceKeys.schoolClassId] ?: 0

            SubjectAndClass(
                schoolSubject,
                schoolSubjectId,
                schoolClass,
                schoolClassId
            )
        }


}

data class SubjectAndClass(
    val schoolSubject: String,
    val schoolSubjectId: Int,
    val schoolClass: String,
    val schoolClassId: Int
)