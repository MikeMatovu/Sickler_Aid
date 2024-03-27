package com.micodes.sickleraid.data.datasource.api.callUtil

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api : ApiCalls by lazy{

        Retrofit.Builder()
            .baseUrl("https://c6bb9dd3-3cd3-41d7-808e-7f7b3f10893e.mock.pstmn.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiCalls::class.java)

    }

}
