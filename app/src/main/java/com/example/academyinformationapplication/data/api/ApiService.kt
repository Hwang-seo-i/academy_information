package com.example.academyinformationapplication.data.api

import androidx.lifecycle.MutableLiveData
import com.example.academyinformationapplication.data.model.AcademyDataResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    companion object {
        const val EndPoint = "/api/15016662/v1/uddi:cfb2f83e-9a62-4824-9ffe-d726d1c306e0"
        const val API_KEY = "dWlnRlkys53ioo5o/cqHUbX34pPbJIsMWlIist5SmrpNbShgDR/hj7+JMl2/xYmRx4cXcWhrfsDff7g+Jh2YcQ=="
    }

    @GET(EndPoint)
    suspend fun getAcademyData(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int,
        @Query("serviceKey") serviceKey: String = API_KEY
    ): Response<AcademyDataResponse>
}