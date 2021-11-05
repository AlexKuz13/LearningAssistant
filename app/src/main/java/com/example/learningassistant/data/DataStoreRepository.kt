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
import java.util.*
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

        val langCode = stringPreferencesKey(PREFERENCES_LANG_CODE)
        val langRBId = intPreferencesKey(PREFERENCES_LANG_RB_ID)
        val language = stringPreferencesKey(PREFERENCES_LANGUAGE)

    }

    private val dataStore: DataStore<Preferences> = context.dataStore
    private val listOfLang = listOf("en", "ru", "de", "fr")

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

    suspend fun saveLangCodeAndId(
        langCode: String,
        langRBId: Int,
        language: String
    ) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.langCode] = langCode
            preferences[PreferenceKeys.langRBId] = langRBId
            preferences[PreferenceKeys.language] = language
        }
    }


    val readLangCodeAndId: Flow<LangCodeAndId> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            var bool = false
            listOfLang.forEach { if (Locale.getDefault().language.contains(it, false)) bool = true }
            val langCode =
                preferences[PreferenceKeys.langCode] ?: if (bool)
                    Locale.getDefault().language else DEFAULT_LANG_CODE
            val langRBId = preferences[PreferenceKeys.langRBId] ?: 0
            val language = preferences[PreferenceKeys.language] ?: if (bool)
                Locale.getDefault().displayLanguage else DEFAULT_LANGUAGE
            LangCodeAndId(
                langCode,
                langRBId,
                language
            )
        }
}

data class SubjectAndClass(
    val schoolSubject: String,
    val schoolSubjectId: Int,
    val schoolClass: String,
    val schoolClassId: Int
)

data class LangCodeAndId(
    val langCode: String,
    val langRBId: Int,
    val language: String
)