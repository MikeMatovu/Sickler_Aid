package com.micodes.sickleraid.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PatientTable")
data class PatientTable(

    @PrimaryKey(autoGenerate = true)
    val id: Int

)