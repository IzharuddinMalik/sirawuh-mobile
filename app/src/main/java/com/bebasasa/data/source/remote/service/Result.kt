package com.bebasasa.data.source.remote.service

import android.content.SharedPreferences
import com.bebasasa.BuildConfig
import com.bebasasa.data.source.local.PreferenceHelper
import com.bebasasa.data.source.remote.response.ApiResponse
import com.bebasasa.data.source.remote.response.ErrorBody
import com.bebasasa.utils.JsonParser
import com.bebasasa.utils.NoNetworkException
import com.bebasasa.utils.StringConstant.GENERAL_ERROR_MESSAGE
import com.bebasasa.utils.StringConstant.INTERNET_ISSUE_MESSAGE
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.CancellationException

sealed class Result<out R> {

    /** Triggered when fetching success*/
    class Success<T>(val data: T?) : Result<T>()

    /** Triggered when fetching failure
     * @param message
     */
    class Error(val message: String?) : Result<Nothing>()

    /** return true when [Error] occurs*/
    val isFailed get() = this is Error

    /** return true when [Success] occurs*/
    val isSuccess get() = this is Success<*>

    val isUploading get() = false

    /** return data wrapped by [Success]*/
    val getData get() = if (this is Success<*>) data else null

    /** return error message wrapped by [Error]*/
    val getErrorMessage get() = if (this is Error) message else null

    companion object {

        /**
         * Handling error by recording the [error] into Firebase Crashlytics.
         * @param label is the label for recorded error key
         * @param body is the request body
         * @param error is the error
         * @return [Result.Error] containing string resource id
         * */
        fun <T> createError(
            label: String,
            body: Any? = null,
            error: Throwable,
        ): Result<T> {
            recordException(error, label, body)
            if (BuildConfig.DEBUG) println("check error ${error.message} error type ${error.javaClass}")

            val message = when (error) {
                is NoNetworkException -> INTERNET_ISSUE_MESSAGE
                is SocketTimeoutException -> INTERNET_ISSUE_MESSAGE
                is HttpException -> getHttpErrorMessage(error)
                else -> GENERAL_ERROR_MESSAGE
            }
            return Error(message)
        }

        fun recordException(e: Throwable, label: String, body: Any?) {
            kotlin.runCatching {
                val internetIssue = e is NoNetworkException
                        || e is SocketTimeoutException
                        || e is UnknownHostException
                        || e is SocketException

                if (internetIssue || e is CancellationException) return

                if (e is HttpException && e.code() == HttpURLConnection.HTTP_UNAUTHORIZED) return
            }
        }

        fun getHttpErrorMessage(error: HttpException): String {
            val errorBody = error.response()?.errorBody()?.string()
            println("check error body $errorBody")
            val result = kotlin.runCatching {
                JsonParser.stringToObject(errorBody!!, ErrorBody::class.java).error
            }.getOrDefault(GENERAL_ERROR_MESSAGE)
            return result.ifEmpty { GENERAL_ERROR_MESSAGE }
        }

        /**
         * Handling success response and jwt derived from the response.
         * When [ApiResponse.jwt] exists, this function will replace the jwt in the [SharedPreferences]
         * @param response
         * - [ApiResponse.data]
         * - [ApiResponse.jwt]
         * - [ApiResponse.error]
         * @return
         * - [Result.Success] data response who want using data remote or empty data when status code is 200
         * - [Result.Error] when status code is not 200
         * */
        fun <T> createResponse(response: ApiResponse<T>, preference: PreferenceHelper): Result<T> {
            response.jwt?.let { preference.saveNipNisSession(it) }
            return Success(response.data)
        }

        /**
         * Handling success response and jwt derived from the response.
         * When [ApiResponse.jwt] exists, this function will replace the jwt in the [SharedPreferences]
         * @param
         * - [ApiResponse.data]
         * - [ApiResponse.jwt]
         * - [ApiResponse.error]
         * @return
         * - [Result.Success] data response who want implement to UI when status code is 200
         * - [Result.Error] when status code is not 200*/
        fun <T> createSuccess(
            data: T?,
            preference: PreferenceHelper? = null,
            jwt: String? = null,
        ): Result<T> {
            jwt?.let { preference?.saveNipNisSession(it) }
            return Success(data)
        }
    }
}
