package com.micodes.sickleraid.data.repository

import com.micodes.sickleraid.data.datasource.database.SicklerDao
import com.micodes.sickleraid.domain.repository.SicklerRepository

open class SicklerRepositoryImpl(
    val dao: SicklerDao
) : SicklerRepository{
}