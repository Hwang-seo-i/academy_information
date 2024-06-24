package com.example.academyinformationapplication.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AcademyDataResponse(
    @SerializedName("currentCount")
    val currentCount: Int,
    @SerializedName("data")
    val data: List<AcademyData>,
    @SerializedName("matchCount")
    val matchCount: Int,
    @SerializedName("page")
    val page: Int,
    @SerializedName("perPage")
    val perPage: Int,
    @SerializedName("totalCount")
    val totalCount: Int
)

@Serializable
data class AcademyData(
    @SerializedName("교습계열")
    val teachingProfession: String,
    @SerializedName("교습과목(반)")
    val teachingCourse: String,
    @SerializedName("교습과정")
    val teachingProcess: String,
    @SerializedName("교습기간")
    val teachingPeriod: String,
    @SerializedName("분야구분")
    val fieldCategory: String,
    @SerializedName("전화번호")
    val phoneNumber: String,
    @SerializedName("정원")
    val capacity: String,
    @SerializedName("차량비")
    val vehicleExpense: String,
    @SerializedName("총교습비")
    val totalTeachingExpense: String,
    @SerializedName("총교습시간(분)")
    val totalTeachingTime: String,
    @SerializedName("학원명")
    val academyName: String,
    @SerializedName("학원종류")
    val academyType: String,
    @SerializedName("학원주소")
    val academyAddress: String,
): java.io.Serializable

data class AcademyTeachingProcess(
    val academyName: String,
    val academyAddress: String,
    val teachingProcess: String,
    var teachingCourseList: List<AcademyData> = listOf()
): java.io.Serializable