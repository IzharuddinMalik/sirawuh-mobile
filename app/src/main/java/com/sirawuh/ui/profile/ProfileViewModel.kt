package com.sirawuh.ui.profile

import androidx.lifecycle.viewModelScope
import com.sirawuh.core.base.BaseViewModel
import com.sirawuh.data.domain.DataProfileResponse
import com.sirawuh.data.domain.GeneralResponse
import com.sirawuh.data.repository.interfaces.AuthRepository
import com.sirawuh.data.source.local.PreferenceHelper
import com.sirawuh.data.source.remote.service.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val preferenceHelper: PreferenceHelper
): BaseViewModel() {

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error

    private val _profileResp = MutableStateFlow<DataProfileResponse?>(null)
    val profileResp: StateFlow<DataProfileResponse?> = _profileResp

    fun initState(){
        RequestHandler().getProfile()
    }

    inner class RequestHandler {
        fun getProfile() {
            val requestBody: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("nipnis", preferenceHelper.getNipNisSession()!!)
                .build()

            handleGetProfile(
                requestBody,
                onSuccess = {
                    _profileResp.value = it
                },
                onError = {
                    _error.update { it }
                }
            )
        }

        private fun handleGetProfile(
            requestBody: RequestBody,
            onSuccess: (DataProfileResponse?) -> Unit,
            onError: (String) -> Unit
        ) {
            viewModelScope.launch {
                authRepository.getProfile(requestBody)
                    .onStart { showTransparentLoading() }
                    .onCompletion { dismissTransparentLoading() }
                    .collect{result ->
                        when(result) {
                            is Result.Success -> {
                                onSuccess(result.data?.data)
                            }

                            is Result.Error -> {
                                onError(result.getErrorMessage.orEmpty())
                            }
                        }
                    }
            }
        }
    }
}