package com.micodes.sickleraid.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="SetUpTable" )
data class SetUp (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val Done: Int,
    val Date:String

)