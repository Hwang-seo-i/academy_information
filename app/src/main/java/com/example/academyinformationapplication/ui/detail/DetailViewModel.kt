package com.example.academyinformationapplication.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.academyinformationapplication.data.model.AcademyData
import com.example.academyinformationapplication.data.model.AcademyTeachingProcess

class DetailViewModel : ViewModel() {
    val academyData = MutableLiveData<List<AcademyData>>()
    val academyError = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

    fun refresh(data: AcademyTeachingProcess) {
        loading.value = true
        academyData.postValue(data.teachingCourseList)
    }
}
