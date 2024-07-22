package com.sirawuh.ui.kelas.kaskelas

import android.annotation.SuppressLint
import androidx.lifecycle.viewModelScope
import com.sirawuh.core.base.BaseViewModel
import com.sirawuh.data.domain.DataKasKelasSiswaResponse
import com.sirawuh.data.domain.GeneralResponse
import com.sirawuh.data.repository.interfaces.MuridKasKelasRepository
import com.sirawuh.data.source.local.PreferenceHelper
import com.sirawuh.data.source.remote.service.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class KasKelasViewModel @Inject constructor(
    private val kasKelasRepository: MuridKasKelasRepository,
    private val preferenceHelper: PreferenceHelper
): BaseViewModel() {

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error

    private val _isEmpty = MutableStateFlow<Boolean?>(false)
    val isEmpty: StateFlow<Boolean?> = _isEmpty

    private val _isButtonEnable = MutableStateFlow<Boolean?>(false)
    val isButtonEnable: StateFlow<Boolean?> = _isButtonEnable

    private val _tambahKasKelasResp = MutableStateFlow<GeneralResponse?>(null)
    val tambahKasKelasResp: StateFlow<GeneralResponse?> = _tambahKasKelasResp

    private val _listKasKelasResp = MutableStateFlow<List<DataKasKelasSiswaResponse>?>(null)
    val listKasKelasResp: StateFlow<List<DataKasKelasSiswaResponse>?> = _listKasKelasResp

    private val _tanggalBayarKasOriginal = MutableStateFlow("")
    val tanggalBayarKasOriginal: StateFlow<String> = _tanggalBayarKasOriginal

    private val _tanggalBayarKasConvert = MutableStateFlow("")
    val tanggalBayarKasConvert: StateFlow<String> = _tanggalBayarKasConvert

    inner class RequestHandler {
        fun sendBayarKas() {
            val requestBody: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("nis", preferenceHelper.getNipNisSession()!!)
                .addFormDataPart("tanggalbayarkas", tanggalBayarKasOriginal.value)
                .build()

            handleSendBayarKas(
                requestBody,
                onSuccess = {
                    _tambahKasKelasResp.value = it
                },
                onError = {
                    _error.update { it }
                }
            )
        }

        private fun handleSendBayarKas(
            requestBody: RequestBody,
            onSuccess: (GeneralResponse?) -> Unit,
            onError: (String) -> Unit
        ) {
            viewModelScope.launch {
                kasKelasRepository.tambahKasKelas(requestBody)
                    .onStart { showTransparentLoading() }
                    .onCompletion { dismissTransparentLoading() }
                    .collect{result ->
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

        fun getListKasKelas() {
            val requestBody: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("nis", preferenceHelper.getNipNisSession()!!)
                .build()

            handleListKasKelas(
                requestBody,
                onSuccess = {
                    handleDataListKasKelas(it)
                },
                onError = {
                    _error.update { it }
                }
            )
        }

        private fun handleListKasKelas(
            requestBody: RequestBody,
            onSuccess: (List<DataKasKelasSiswaResponse>?) -> Unit,
            onError: (String) -> Unit
        ) {
            viewModelScope.launch {
                kasKelasRepository.getInfoKasKelas(requestBody)
                    .onStart { showTransparentLoading() }
                    .onCompletion { dismissTransparentLoading() }
                    .collect{result ->
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

        private fun handleDataListKasKelas(listKasKelas: List<DataKasKelasSiswaResponse>?) {
            if (listKasKelas!!.isEmpty()) {
                _isEmpty.update { true }
            } else {
                _isEmpty.update { false }
                _listKasKelasResp.value = listKasKelas
            }
        }
    }

    inner class StateHandler {
        @SuppressLint("SimpleDateFormat")
        fun convertDate(time: Long) {
            val date = Date(time)
            val format = SimpleDateFormat("dd-MM-yyyy")
            _tanggalBayarKasConvert.value = format.format(date)

            val dateOriginal = Date(time)
            val formatOriginal = SimpleDateFormat("yyyy-MM-dd")
            _tanggalBayarKasOriginal.value = formatOriginal.format(dateOriginal)
        }
    }
}