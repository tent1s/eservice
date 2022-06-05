package ru.omsu.eservice.data.device.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.omsu.eservice.domain.model.EducationGroupUi

class EducationCardConvector {

    @TypeConverter
    fun fromEducationCardUi(educationGroupUi: EducationGroupUi) : String =
        Gson().toJson(educationGroupUi)

    @TypeConverter
    fun toEducationCardUi(string: String) : EducationGroupUi =
        Gson().fromJson(string, EducationGroupUi::class.java)

}