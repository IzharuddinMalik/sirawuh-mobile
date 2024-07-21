package com.bebasasa.ui.login

import androidx.lifecycle.viewModelScope
import com.bebasasa.core.base.BaseViewModel
import com.bebasasa.data.domain.LoginResponse
import com.bebasasa.data.repository.interfaces.AuthRepository
import com.bebasasa.data.source.remote.service.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private val _loginResp = MutableStateFlow<LoginResponse?>(null)
    val loginResp: StateFlow<LoginResponse?> = _loginResp

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error

    private val _nipnisValid = MutableStateFlow(false)
    val nipnisValid: StateFlow<Boolean> = _nipnisValid

    private val _isButtonEnable = MutableStateFlow(false)
    val isButtonEnable: StateFlow<Boolean> = _isButtonEnable

    var nipnis = MutableStateFlow("")

    fun initState() {
        viewModelScope.launch {
            combine(
                nipnis
            ) { fieldArray ->
                _isButtonEnable.update {
                    areFieldsNotEmpty(fieldArray)
                }
            }.collect()
        }
    }

    private fun areFieldsNotEmpty(fieldsArray: Array<String>): Boolean {
        return fieldsArray.all { it.isNotEmpty() }
    }

    inner class RequestHandler {
        fun login() {
            val requestBody: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("nipnis", nipnis.value)
                .build()

            handleLogin(requestBody,
                onSuccess = {
                    _loginResp.value = it
                },
                onError = {
                    _error.update { it }
                })
        }

        private fun handleLogin(
            loginRequest: RequestBody,
            onSuccess: (LoginResponse?) -> Unit,
            onError: (String) -> Unit
        ) {
            viewModelScope.launch {
                authRepository.login(loginRequest)
                    .onStart { showTransparentLoading() }
                    .onCompletion { dismissTransparentLoading() }
                    .collect { result ->
                        when (result) {
                            is Result.Success -> {
                                onSuccess(result.data)
                            }

                            is Result.Error -> {
                                onError(result.getErrorMessage.orEmpty())
                            }
                        }
                    }
            }
        }
    }

    inner class StateHandler {
        fun setNipNis(value: String) {
            nipnis.update { value }
            val isValid = value.run {
                isEmpty()
            }
            _nipnisValid.update { isValid }
        }
    }
}