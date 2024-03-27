package com.micodes.sickleraid.data.datasource.api

import com.micodes.sickleraid.data.datasource.api.callUtil.RetrofitInstance

class MakeRequest{

    suspend operator  fun invoke(string: String ): Any? {
        var response: Any? = null
        //Checknet().checkNet()

        when (string) {

            "getAll" -> {

                response = try {
                    RetrofitInstance.api.getAllTransactions().body()
                } catch (e: Exception) {
                    return null
                }

            }

            "getSize" -> {
                response = try {
                    RetrofitInstance.api.getSize().body()
                } catch (e: Exception) {

                    return null
                }
            }

        }

        return response
    }
}