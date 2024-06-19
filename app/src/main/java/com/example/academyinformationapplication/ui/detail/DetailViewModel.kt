package com.example.academyinformationapplication.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.academyinformationapplication.data.api.RetrofitClient
import com.example.academyinformationapplication.data.model.AcademyData
import com.example.academyinformationapplication.data.model.AcademyDataResponse
import com.example.academyinformationapplication.data.model.AcademyTeachingProcess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class DetailViewModel : ViewModel() {
    private val academyService = RetrofitClient.getRetrofitService()

    val academyData = MutableLiveData<List<AcademyData>>()
    val academyError = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

    fun refresh(data: AcademyTeachingProcess) {
        loading.value = true

        academyData.postValue(data.teachingCourseList)

//        viewModelScope.launch {
//            try {
//                // 서버에서 데이터 가져오기
//                val response = withContext(Dispatchers.IO) {
//                    academyService.getAcademyData(1, 100)
//                }
//
//                if (response.isSuccessful) {
//                    val filteredData = response.body()?.data?.filter {
//                        it.academyName == academyName && it.teachingProcess == teachingProcess
//                    } ?: emptyList()
//                    academyData.postValue(filteredData)
//                    academyError.postValue(null)
//                } else {
//                    onError("Error: ${response.message()}")
//                }
//            } catch (e: IOException) {
//                onError("IOException: ${e.localizedMessage}")
//            } catch (e: HttpException) {
//                onError("HttpException: ${e.localizedMessage}")
//            } catch (e: Exception) {
//                onError("Exception: ${e.localizedMessage}")
//            } finally {
//                loading.postValue(false)
//            }
//        }
    }

    private fun onError(message: String) {
        academyError.postValue(message)
        loading.postValue(false)
    }
}
