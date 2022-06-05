package ru.omsu.eservice.data.device.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.omsu.eservice.domain.model.EducationGroupUi

@Entity(tableName = "education_card_table")
@TypeConverters(EducationCardConvector::class)
data class EducationCardData(
    @PrimaryKey(autoGenerate = false)
    var groupName: String,
    var educationGroupUi: EducationGroupUi
)