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
    @SerialName("강사수")
    val teacherCount: String,
    @SerialName("교습계열")
    val teachingProfession: String,
    @SerialName("교습과목(반)")
    val teachingCourse: String,
    @SerializedName("교습과정")
    val teachingProcess: String,
    @SerialName("교습기간")
    val teachingPeriod: String,
    @SerialName("급식비")
    val mealExpense: String,
    @SerialName("기숙사비")
    val dormitoryExpense: String,
    @SerialName("기타경비합계")
    val totalExpense: String,
    @SerialName("모의고사비")
    val mockTestExpense: String,
    @SerialName("분야구분")
    val fieldCategory: String,
    @SerialName("설립자-성명")
    val founderName: String,
    @SerialName("재료비")
    val materialExpense: String,
    @SerializedName("전화번호")
    val phoneNumber: String,
    @SerialName("정원")
    val capacity: String,
    @SerialName("차량비")
    val vehicleExpense: String,
    @SerialName("총교습비")
    val totalTeachingExpense: String,
    @SerialName("총교습시간(분)")
    val totalTeachingTime: String,
    @SerialName("피복비")
    val uniformExpense: String,
    @SerializedName("학원명")
    val academyName: String,
    @SerializedName("학원종류")
    val academyType: String,
    @SerializedName("학원주소")
    val academyAddress: String
): java.io.Serializable

data class AcademyTeachingProcess(
    val academyName: String,
    val academyAddress: String,
    val teachingProcess: String,
    var teachingCourseList: List<AcademyData> = listOf()
): java.io.Serializable
