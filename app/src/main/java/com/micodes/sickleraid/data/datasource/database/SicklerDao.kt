package com.micodes.sickleraid.data.datasource.database

import androidx.room.Dao
import java.util.Date

@Dao
interface SicklerDao{

//      EXAMPLE
//    @Query("SELECT * FROM  TRANSACTION_TABLE LIMIT 1000")
//     fun getTransactions(): Flow<List<Transactions>>

}

fun getCurrentDateTime(): Date {
    return Date()
}
