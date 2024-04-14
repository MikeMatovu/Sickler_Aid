package com.micodes.sickleraid.data.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.micodes.sickleraid.domain.model.MedicineTable

@Dao
interface MedicineDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedicine(medicineTable: MedicineTable)

    @Query("SELECT * FROM medicines WHERE userId = :userId")
    fun getMedicines(userId: String): List<MedicineTable>

}