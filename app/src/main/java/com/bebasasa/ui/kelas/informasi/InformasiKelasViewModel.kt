package com.bebasasa.ui.kelas.informasi

import androidx.lifecycle.viewModelScope
import com.bebasasa.core.base.BaseViewModel
import com.bebasasa.data.domain.GeneralResponse
import com.bebasasa.data.domain.ItemInformasiKelasResp
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
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class InformasiKelasViewModel @Inject constructor(
    private val informasiKelasRepository: InformasiKelasRepository
) : BaseViewModel() {

    private val _buatInformasiResp = MutableStateFlow<GeneralResponse?>(null)
    val buatInformasiResp: StateFlow<GeneralResponse?> = _buatInformasiResp

    private val _listInformasiResp = MutableStateFlow<List<ItemInformasiKelasResp>?>(null)
    val listInformasiKelas: StateFlow<List<ItemInformasiKelasResp>?> = _listInformasiResp

    private val _ubahInformasiResp = MutableStateFlow<GeneralResponse?>(null)
    val ubahInformasiResp: StateFlow<GeneralResponse?> = _ubahInformasiResp

    private val _hapusInformasiResp = MutableStateFlow<GeneralResponse?>(null)
    val hapusInformasiResp: StateFlow<GeneralResponse?> = _hapusInformasiResp

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error

    private val _isEmpty = MutableStateFlow<Boolean?>(false)
    val isEmpty: StateFlow<Boolean?> = _isEmpty

    private val _isButtonEnable = MutableStateFlow<Boolean?>(false)
    val isButtonEnable: StateFlow<Boolean?> = _isButtonEnable

    private val _judulInformasiValid = MutableStateFlow<Boolean?>(false)
    val judulInformasiValid: StateFlow<Boolean?> = _judulInformasiValid

    private val _deskripsiInformasiValid = MutableStateFlow<Boolean?>(false)
    val deskripsiInformasiValid: StateFlow<Boolean?> = _deskripsiInformasiValid

    var idPapanInformasi = MutableStateFlow("")
    var judulInformasi = MutableStateFlow("")
    var deskripsiInformasi = MutableStateFlow("")
    var mediaInformasiFile = MutableStateFlow<File?>(null)
    var mediaInformasiPath = MutableStateFlow("")

    fun initState() {
        viewModelScope.launch {
            combine(
                judulInformasi,
                deskripsiInformasi
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

        fun sendBuatInformasi() {
            val requestBody: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("judulinformasi", judulInformasi.value)
                .addFormDataPart("deskripsiinformasi", deskripsiInformasi.value)
                .addFormDataPart(
                    "mediainformasi",
                    mediaInformasiPath.value,
                    RequestBody.create("*/*".toMediaTypeOrNull(), mediaInformasiFile.value!!)
                )
                .build()

            handlerBuatInformasi(
                requestBody,
                onSuccess = {
                    _buatInformasiResp.value = it
                },
                onError = {
                    _error.update { it }
                }
            )
        }

        private fun handlerBuatInformasi(
            requestBody: RequestBody,
            onSuccess: (GeneralResponse?) -> Unit,
            onError: (String) -> Unit
        ) {
            viewModelScope.launch {
                informasiKelasRepository.buatInformasi(requestBody)
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

        fun getListInformasi() {
            viewModelScope.launch {
                informasiKelasRepository.getPapanInformasi()
                    .onStart { showTransparentLoading() }
                    .onCompletion { dismissTransparentLoading() }
                    .collect { result ->
                        when (result) {
                            is Result.Success -> {
                                handleListInformasi(result.data?.data)
                            }

                            is Result.Error -> {
                                _error.update { result.getErrorMessage.orEmpty() }
                            }
                        }
                    }
            }
        }

        private fun handleListInformasi(listInformasiKelas: List<ItemInformasiKelasResp>?) {
            if (listInformasiKelas!!.isEmpty()) {
                _isEmpty.update { true }
            } else {
                _isEmpty.update { false }
                _listInformasiResp.value = listInformasiKelas
            }
        }

        fun ubahInformasi() {
            val requestBody: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("idpapaninformasi", idPapanInformasi.value)
                .addFormDataPart("judulinformasi", judulInformasi.value)
                .addFormDataPart("deskripsiinformasi", deskripsiInformasi.value)
                .addFormDataPart(
                    "mediainformasi",
                    mediaInformasiPath.value,
                    RequestBody.create("*/*".toMediaTypeOrNull(), mediaInformasiFile.value!!)
                )
                .build()

            handleUbahInformasi(
                requestBody,
                onSuccess = {
                    _ubahInformasiResp.value = it
                },
                onError = {
                    _error.update { it }
                }
            )
        }

        private fun handleUbahInformasi(
            requestBody: RequestBody,
            onSuccess: (GeneralResponse?) -> Unit,
            onError: (String) -> Unit
        ) {
            viewModelScope.launch {
                informasiKelasRepository.ubahInformasi(requestBody)
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

        fun hapusInformasi() {
            val requestBody: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("idpapaninformasi", idPapanInformasi.value)
                .build()

            handleHapusInformasi(
                requestBody,
                onSuccess = {
                    _hapusInformasiResp.value = it
                },
                onError = {
                    _error.update { it }
                }
            )
        }

        private fun handleHapusInformasi(
            requestBody: RequestBody,
            onSuccess: (GeneralResponse?) -> Unit,
            onError: (String) -> Unit
        ) {
            viewModelScope.launch {
                informasiKelasRepository.hapusInformasi(requestBody)
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
        fun setIdPapanInformasi(value: String) {
            idPapanInformasi.update { value }
        }

        fun setJudulInformasi(value: String) {
            judulInformasi.update { value }
            val isValid = value.run {
                isEmpty()
            }
            _judulInformasiValid.update { isValid }
        }

        fun setDeskripsiInformasi(value: String) {
            deskripsiInformasi.update { value }
            val isValid = value.run {
                isEmpty()
            }
            _deskripsiInformasiValid.update { isValid }
        }

        fun mediaInformasi(value: File, path: String) {
            mediaInformasiFile.update { value }
            mediaInformasiPath.update { path }
        }
    }
}
