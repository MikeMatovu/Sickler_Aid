package com.micodes.sickleraid.data.repository

import com.micodes.sickleraid.data.datasource.database.SicklerAidDao
import com.micodes.sickleraid.domain.repository.SicklerRepository

open class SicklerRepositoryImpl(
    val dao: SicklerAidDao
) : SicklerRepository{
}