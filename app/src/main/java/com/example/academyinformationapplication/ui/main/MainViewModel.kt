package com.example.academyinformationapplication.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.academyinformationapplication.data.api.RetrofitClient
import com.example.academyinformationapplication.data.model.AcademyData
import com.example.academyinformationapplication.data.model.AcademyTeachingProcess
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {
    private val academyService = RetrofitClient.getRetrofitService()
    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception: ${throwable.localizedMessage}")
    }

    var groupedData = MutableLiveData<List<AcademyTeachingProcess>>()
    var data = MutableLiveData<List<AcademyData>>()
    val academyError = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

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

    private fun groupByAcademy(data: List<AcademyData>): List<AcademyTeachingProcess> {
        return data.groupBy {
            AcademyTeachingProcess(
                it.academyName,
                it.academyAddress,
                it.teachingProcess
            )
        }.map {
                it.key.teachingCourseList = it.value

                it.key
            }


//            .map { (key, value) ->
//                val (academyName, teachingProcess) = key
//                val academyAddresses = value.map { it.academyAddress }
//                val teachingProfessions = value.map { it.teachingProfession }
//                val teachingCourses = value.map { it.teachingCourse }
//                val teachingPeriods = value.map { it.teachingPeriod }
//                val totalExpenses = value.map { it.totalExpense }
//
//                AcademyTeachingProcess(
//                    academyName,
//                    academyAddresses,
//                    teachingProcess,
//                    teachingProfessions,
//                    teachingCourses,
//                    teachingPeriods,
//                    totalExpenses
//                )
//            }
    }

    private fun onError(message: String) {
        academyError.postValue(message)
        loading.postValue(false)
    }
}