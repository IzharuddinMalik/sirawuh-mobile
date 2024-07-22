package com.sirawuh.ui.kelas.kehadiran

import androidx.lifecycle.viewModelScope
import com.sirawuh.core.base.BaseViewModel
import com.sirawuh.data.domain.DataKehadiranSiswaResponse
import com.sirawuh.data.domain.DataPengumumanResponse
import com.sirawuh.data.domain.DataStatusKehadiranResponse
import com.sirawuh.data.domain.GeneralResponse
import com.sirawuh.data.domain.KehadiranSiswaResponse
import com.sirawuh.data.repository.interfaces.MuridKehadiranRepository
import com.sirawuh.data.source.local.PreferenceHelper
import com.sirawuh.data.source.remote.service.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.security.PrivateKey
import javax.inject.Inject

@HiltViewModel
class KehadiranViewModel @Inject constructor(
    private val kehadiranRepository: MuridKehadiranRepository,
    private val preferencesHelper: PreferenceHelper
): BaseViewModel() {

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error

    private val _isEmpty = MutableStateFlow<Boolean?>(false)
    val isEmpty: StateFlow<Boolean?> = _isEmpty

    private val _isButtonEnable = MutableStateFlow<Boolean?>(false)
    val isButtonEnable: StateFlow<Boolean?> = _isButtonEnable

    private val _tambahKehadiranResp = MutableStateFlow<GeneralResponse?>(null)
    val tambahKehadiranResp: StateFlow<GeneralResponse?> = _tambahKehadiranResp

    private val _listKehadiranResp = MutableStateFlow<List<DataKehadiranSiswaResponse>?>(null)
    val listKehadiranResp: StateFlow<List<DataKehadiranSiswaResponse>?> = _listKehadiranResp

    private val _listStatusKehadiran = MutableStateFlow<List<DataStatusKehadiranResponse>?>(null)
    val listStatusKehadiran: StateFlow<List<DataStatusKehadiranResponse>?> = _listStatusKehadiran

    private val _statusKehadiranValid = MutableStateFlow<Boolean?>(false)
    val statusKehadiranValid: StateFlow<Boolean?> = _statusKehadiranValid

    private val _fotoKehadiranValid = MutableStateFlow<Boolean?>(false)
    val fotoKehadiranValid: StateFlow<Boolean?> = _fotoKehadiranValid

    var statusKehadiran = MutableStateFlow("")
    var fotoBuktiFile = MutableStateFlow<File?>(null)
    var fotoBuktiPath = MutableStateFlow("")

    fun initState() {
        viewModelScope.launch {
            combine(
                statusKehadiran,
                fotoBuktiPath
            ) { fieldsArray ->
                _isButtonEnable.update {
                    areFieldsNotEmpty(fieldsArray)
                }
            }.collect()
        }
    }

    private fun areFieldsNotEmpty(fieldsArray: Array<String>): Boolean {
        return fieldsArray.all { it.isNotEmpty() }
    }

    inner class RequestHandler {

        fun sendTambahKehadiran() {
            val requestBody: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("nis", preferencesHelper.getNipNisSession()!!)
                .addFormDataPart("statuskehadiran", statusKehadiran.value)
                .addFormDataPart(
                    "fotokehadiran",
                    fotoBuktiPath.value,
                    RequestBody.create("*/*".toMediaTypeOrNull(), fotoBuktiFile.value!!)
                )
                .build()

            handlerTambahKehadiran(
                requestBody,
                onSuccess = {
                    _tambahKehadiranResp.value = it
                },
                onError = {
                    _error.update { it }
                }
            )
        }

        fun handlerTambahKehadiran(
            requestBody: RequestBody,
            onSuccess: (GeneralResponse?) -> Unit,
            onError: (String) -> Unit) {

            viewModelScope.launch {
                kehadiranRepository.tambahKehadiran(requestBody)
                    .onStart {
                        showTransparentLoading()
                    }
                    .onCompletion {
                        dismissTransparentLoading()
                    }
                    .collect{ result ->
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

        fun getListKehadiran() {
            val requestBody: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("nis", preferencesHelper.getNipNisSession()!!)
                .build()

            handlerListKehadiran(
                requestBody,
                onSuccess = {
                    handleListKehadiran(it)
                },
                onError = {
                    _error.update { it }
                }
            )
        }

        private fun handlerListKehadiran(
            requestBody: RequestBody,
            onSuccess: (List<DataKehadiranSiswaResponse>?) -> Unit,
            onError: (String) -> Unit
        ){
            viewModelScope.launch {
                kehadiranRepository.getPresensiKehadiran(requestBody)
                    .onStart { showTransparentLoading() }
                    .onCompletion { dismissTransparentLoading() }
                    .collect {result ->
                        when (result) {
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

        private fun handleListKehadiran(listKehadiran: List<DataKehadiranSiswaResponse>?) {
            if (listKehadiran!!.isEmpty()) {
                _isEmpty.update { true }
            } else {
                _isEmpty.update { false }
                _listKehadiranResp.value = listKehadiran
            }
        }

        fun getStatusKehadiran() {
            viewModelScope.launch {
                kehadiranRepository.getStatusKehadiran()
                    .onStart { showTransparentLoading() }
                    .onCompletion { dismissTransparentLoading() }
                    .collect{result ->
                        when (result) {
                            is Result.Success -> {
                                handleListStatusKehadiran(result.data?.data)
                            }

                            is Result.Error -> {
                                _error.update { result.getErrorMessage.orEmpty() }
                            }
                        }
                    }
            }
        }

        private fun handleListStatusKehadiran(listStatusKehadiran: List<DataStatusKehadiranResponse>?) {
            if (listStatusKehadiran!!.isEmpty()) {
                _isEmpty.update { true }
            } else {
                _isEmpty.update { false }
                _listStatusKehadiran.value = listStatusKehadiran
            }
        }
    }

    inner class StateHandler {

        fun setStatusKehadiran(value: String) {
            statusKehadiran.update { value }
            val isValid = value.run {
                isEmpty()
            }
            _statusKehadiranValid.update { isValid }
        }

        fun setFotoBukti(value: File, path: String) {
            fotoBuktiFile.update { value }
            fotoBuktiPath.update { path }

            val isValid = path.run {
                isEmpty()
            }
            _fotoKehadiranValid.update { isValid }
        }
    }
}