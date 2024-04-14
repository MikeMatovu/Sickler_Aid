package com.micodes.sickleraid.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "medicines",
    foreignKeys = [
        ForeignKey(
            entity = UserDetails::class,
            childColumns = ["userId"],
            parentColumns = ["uid"]
        )
    ]
)
data class MedicineTable(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: String = "",
    val name: String = "",
    val description: String = "",
    val dosage: String = "",
    val frequency: String = "",
    val duration: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
