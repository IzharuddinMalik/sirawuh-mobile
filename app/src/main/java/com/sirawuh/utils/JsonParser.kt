package com.sirawuh.utils

import com.sirawuh.data.source.remote.response.ApiResponse
import com.google.gson.Gson

object JsonParser {
    fun <T> stringToArray(s: String?, clazz: Class<Array<T>>?): List<T> {
        val arr = Gson().fromJson(s, clazz)
        return arr.toList()
    }

    fun <T> objectToString(data: T): String? {
        return Gson().toJson(data)
    }

    fun <T> stringToObject(data: String, obj: Class<T>): T {
        return Gson().fromJson(data, obj)
    }

    fun <T> createResponseFromString(response: String, obj: Class<T>): ApiResponse<T> {
        val assetSource = this::class.java.classLoader!!.getResource(response).readText()
        val responseResult = stringToObject(data = assetSource, ApiResponse::class.java)
        val dataParsed = objectToString(responseResult.data)!!
        val data = stringToObject(dataParsed, obj)
        return ApiResponse.createResponse(data = data, jwt = null)
    }

    fun <T> createArrayResponseFromString(
        response: String,
        obj: Class<Array<T>>
    ): ApiResponse<List<T>> {
        val assetSource = this::class.java.classLoader!!.getResource(response).readText()
        val responseResult = stringToObject(data = assetSource, ApiResponse::class.java)
        val dataParsed = objectToString(responseResult.data)
        val data = stringToArray(dataParsed, obj)
        return ApiResponse.createResponse(data = data, jwt = null)
    }

    fun <T> createGeneralResponseFromString(fileName: String, obj: Class<T>): T {
        val assetSource = this::class.java.classLoader!!.getResource(fileName).readText()
        return stringToObject(data = assetSource, obj)
    }

    fun getResourceString(fileName: String): String {
        return this::class.java.classLoader!!.getResource(fileName).readText()
    }
}