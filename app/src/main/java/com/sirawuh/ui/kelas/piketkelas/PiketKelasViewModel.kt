package com.sirawuh.ui.kelas.piketkelas

import android.annotation.SuppressLint
import androidx.lifecycle.viewModelScope
import com.sirawuh.core.base.BaseViewModel
import com.sirawuh.data.domain.DataPiketSiswaResponse
import com.sirawuh.data.domain.GeneralResponse
import com.sirawuh.data.repository.interfaces.MuridPiketKelasRepository
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
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class PiketKelasViewModel @Inject constructor(
    private val piketKelasRepository: MuridPiketKelasRepository,
    private val preferenceHelper: PreferenceHelper
): BaseViewModel() {

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error

    private val _isEmpty = MutableStateFlow<Boolean?>(false)
    val isEmpty: StateFlow<Boolean?> = _isEmpty

    private val _isButtonEnable = MutableStateFlow<Boolean?>(false)
    val isButtonEnable: StateFlow<Boolean?> = _isButtonEnable

    private val _tambahPiketResp = MutableStateFlow<GeneralResponse?>(null)
    val tambahPiketResp: StateFlow<GeneralResponse?> = _tambahPiketResp

    private val _listPiketResp = MutableStateFlow<List<DataPiketSiswaResponse>?>(null)
    val listPiketResp: StateFlow<List<DataPiketSiswaResponse>?> = _listPiketResp

    private val _tanggalPiketOriginal = MutableStateFlow("")
    val tanggalPiketOriginal: StateFlow<String> = _tanggalPiketOriginal

    private val _tanggalPiketConvert = MutableStateFlow("")
    val tanggalPiketConvert: StateFlow<String> = _tanggalPiketConvert

    private val _fotoPiketValid = MutableStateFlow<Boolean?>(false)
    val fotoPiketValid: StateFlow<Boolean?> = _fotoPiketValid

    var fotoBuktiFile = MutableStateFlow<File?>(null)
    var fotoBuktiPath = MutableStateFlow("")

    fun initState() {
        viewModelScope.launch {
            combine(
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
        fun sendPiket() {
            val requestBody: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("nis", preferenceHelper.getNipNisSession()!!)
                .addFormDataPart("tanggalpiket", tanggalPiketOriginal.value)
                .addFormDataPart(
                    "fotopiket",
                    fotoBuktiPath.value,
                    RequestBody.create("*/*".toMediaTypeOrNull(), fotoBuktiFile.value!!)
                )
                .build()

            handleSendPiket(
                requestBody,
                onSuccess = {
                    _tambahPiketResp.value = it
                },
                onError = {
                    _error.update { it }
                }
            )
        }

        private fun handleSendPiket(
            requestBody: RequestBody,
            onSuccess: (GeneralResponse?) -> Unit,
            onError: (String) -> Unit
        ) {
            viewModelScope.launch {
                piketKelasRepository.tambahPiketKelas(requestBody)
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

        fun getListPiket() {
            val requestBody: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("nis", preferenceHelper.getNipNisSession()!!)
                .build()

            handleListPiket(
                requestBody,
                onSuccess = {
                    handleDataListPiket(it)
                },
                onError = {
                    _error.update { it }
                }
            )
        }

        private fun handleListPiket(
            requestBody: RequestBody,
            onSuccess: (List<DataPiketSiswaResponse>?) -> Unit,
            onError: (String) -> Unit
        ) {
            viewModelScope.launch {
                piketKelasRepository.getInfoPiketKelas(requestBody)
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

        private fun handleDataListPiket(listPiket: List<DataPiketSiswaResponse>?) {
            if (listPiket!!.isEmpty()) {
                _isEmpty.update { true }
            } else {
                _isEmpty.update { false }
                _listPiketResp.value = listPiket
            }
        }
    }

    inner class StateHandler {

        @SuppressLint("SimpleDateFormat")
        fun convertDate(time: Long) {
            val date = Date(time)
            val format = SimpleDateFormat("dd-MM-yyyy")
            _tanggalPiketConvert.value = format.format(date)

            val dateOriginal = Date(time)
            val formatOriginal = SimpleDateFormat("yyyy-MM-dd")
            _tanggalPiketOriginal.value = formatOriginal.format(dateOriginal)
        }

        fun setFotoBukti(value: File, path: String) {
            fotoBuktiFile.update { value }
            fotoBuktiPath.update { path }

            val isValid = path.run {
                isEmpty()
            }
            _fotoPiketValid.update { isValid }
        }
    }
}