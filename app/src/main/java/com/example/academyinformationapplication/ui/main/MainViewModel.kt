package com.example.academyinformationapplication.ui.main

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.academyinformationapplication.data.api.RetrofitClient
import com.example.academyinformationapplication.data.model.AcademyData
import com.example.academyinformationapplication.data.model.AcademyTeachingProcess
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*

class MainViewModel : ViewModel() {
    private lateinit var preferences: SharedPreferences

    private val academyService = RetrofitClient.getRetrofitService()
    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception: ${throwable.localizedMessage}")
    }

    var groupedData = MutableLiveData<List<AcademyTeachingProcess>>()
    var data = MutableLiveData<List<AcademyData>>()
    val academyError = MutableLiveData<String?>()
    private val loading = MutableLiveData<Boolean>()

    // favoriteList를 LiveData로 설정하여 외부에서 수정할 수 없도록 보호
    private var _favoriteList = MutableLiveData<List<AcademyTeachingProcess>>()
    val favoriteList: LiveData<List<AcademyTeachingProcess>> get() = _favoriteList

    // SharedPreferences 설정 메서드
    fun setPreferences(preferences: SharedPreferences) {
        this.preferences = preferences
        _favoriteList.value = loadFavorites().toMutableList()
    }

    // 데이터 새로고침 메서드
    fun refresh() {
        loading.value = true

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            try {
                val response = academyService.getAcademyData(1, 100)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        Log.e("data", responseBody?.data.toString())

                        data.postValue(responseBody?.data)

                        val grouped = groupByAcademy(responseBody?.data ?: emptyList())
                        groupedData.postValue(grouped)
                        academyError.postValue(null)
                    } else {
                        onError("Error : ${response.message()}")
                    }
                }
            } catch (e: Exception) {
                onError("Exception: ${e.localizedMessage}")
            } finally {
                loading.postValue(false)
            }
        }
    }

    // 즐겨찾기 추가 메서드
    fun addFavorite(academy: AcademyTeachingProcess) {
        val favorites = loadFavorites().toMutableList()
        favorites.add(academy)
        saveFavorites(favorites)
        _favoriteList.value = favorites
        println(">>> Add : ${_favoriteList.value}")
    }

    // 즐겨찾기 제거 메서드
    fun removeFavorite(academy: AcademyTeachingProcess) {
        val favorites = loadFavorites().toMutableList()
        favorites.remove(academy)
        saveFavorites(favorites)
        _favoriteList.postValue(favorites)
        println(">>> Remove : ${_favoriteList.value}")
    }

    // 특정 아카데미의 현재 즐겨찾기 상태 반환 메서드
    fun getCurrentFavoriteState(academy: AcademyTeachingProcess): Boolean {
        val favorites = loadFavorites().toMutableList()
        return favorites.contains(academy)
    }

    // 즐겨찾기 로드 메서드
    private fun loadFavorites(): List<AcademyTeachingProcess> {
        if (!::preferences.isInitialized) {
            throw IllegalStateException("Preferences have not been initialized.")
        }
        val json = preferences.getString("favorites", null) ?: return emptyList()
        val type = object : TypeToken<List<AcademyTeachingProcess>>() {}.type
        return Gson().fromJson(json, type)
    }

    // 즐겨찾기 저장 메서드
    private fun saveFavorites(favorites: List<AcademyTeachingProcess>) {
        val editor = preferences.edit()
        val json = Gson().toJson(favorites)
        editor.putString("favorites", json)
        editor.apply()
    }

    // 데이터 그룹화 메서드
    private fun groupByAcademy(data: List<AcademyData>): List<AcademyTeachingProcess> {
        return data.groupBy {
            AcademyTeachingProcess(
                it.academyName,
                it.academyAddress,
                it.teachingProcess,
                teachingCourseList = listOf()
            )
        }.map {
            it.key.teachingCourseList = it.value
            it.key
        }
    }

    // 오류 처리 메서드
    private fun onError(message: String) {
        academyError.postValue(message)
        loading.postValue(false)
    }
}
