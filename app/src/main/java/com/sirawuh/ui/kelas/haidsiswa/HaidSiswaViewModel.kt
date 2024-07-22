package com.sirawuh.ui.kelas.haidsiswa

import android.annotation.SuppressLint
import androidx.lifecycle.viewModelScope
import com.sirawuh.core.base.BaseViewModel
import com.sirawuh.data.domain.DataHaidSiswaResponse
import com.sirawuh.data.domain.GeneralResponse
import com.sirawuh.data.repository.interfaces.MuridHaidSiswaRepository
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
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HaidSiswaViewModel @Inject constructor(
    private val haidSiswaRepository: MuridHaidSiswaRepository,
    private val preferenceHelper: PreferenceHelper
): BaseViewModel() {

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error

    private val _isEmpty = MutableStateFlow<Boolean?>(false)
    val isEmpty: StateFlow<Boolean?> = _isEmpty

    private val _isButtonEnable = MutableStateFlow<Boolean?>(false)
    val isButtonEnable: StateFlow<Boolean?> = _isButtonEnable

    private val _tambahHaidResp = MutableStateFlow<GeneralResponse?>(null)
    val tambahHaidResp: StateFlow<GeneralResponse?> = _tambahHaidResp

    private val _listSiswaHaidResp = MutableStateFlow<List<DataHaidSiswaResponse>?>(null)
    val listSiswaHaidResp: StateFlow<List<DataHaidSiswaResponse>?> = _listSiswaHaidResp

    private val _tanggalHaidOriginal = MutableStateFlow("")
    val tanggalHaidOriginal: StateFlow<String> = _tanggalHaidOriginal

    private val _tanggalHaidConvert = MutableStateFlow("")
    val tanggalHaidConvert: StateFlow<String> = _tanggalHaidConvert

    fun initState() {
        viewModelScope.launch {
            combine(
                tanggalHaidOriginal,
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
        fun sendHaidSiswa() {
            val requestBody: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("nis", preferenceHelper.getNipNisSession()!!)
                .addFormDataPart("tanggalhaid", tanggalHaidOriginal.value)
                .build()

            handleHaidSiswa(
                requestBody,
                onSuccess = {
                    _tambahHaidResp.value = it
                },
                onError = {
                    _error.update { it }
                }
            )
        }

        private fun handleHaidSiswa(
            requestBody: RequestBody,
            onSuccess: (GeneralResponse?) -> Unit,
            onError: (String) -> Unit
        ) {
            viewModelScope.launch {
                haidSiswaRepository.tambahInfoHaid(requestBody)
                    .onStart { showTransparentLoading() }
                    .onCompletion { dismissTransparentLoading() }
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

        fun getListHaidSiswa() {
            val requestBody: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("nis", preferenceHelper.getNipNisSession()!!)
                .build()

            handleListHaidSiswa(
                requestBody,
                onSuccess = {
                    handleListDataHaidSiswa(it)
                },
                onError = {
                    _error.update { it }
                }
            )
        }

        private fun handleListHaidSiswa(
            requestBody: RequestBody,
            onSuccess: (List<DataHaidSiswaResponse>?) -> Unit,
            onError: (String) -> Unit
        ) {
            viewModelScope.launch {
                haidSiswaRepository.getInfoHaid(requestBody)
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

        private fun handleListDataHaidSiswa(listHaidSiswa: List<DataHaidSiswaResponse>?) {
            if (listHaidSiswa!!.isEmpty()) {
                _isEmpty.update { true }
            } else {
                _isEmpty.update { false }
                _listSiswaHaidResp.value = listHaidSiswa
            }
        }
    }

    inner class StateHandler {
        @SuppressLint("SimpleDateFormat")
        fun convertDate(time: Long) {
            val date = Date(time)
            val format = SimpleDateFormat("dd-MM-yyyy")
            _tanggalHaidConvert.value = format.format(date)

            val dateOriginal = Date(time)
            val formatOriginal = SimpleDateFormat("yyyy-MM-dd")
            _tanggalHaidOriginal.value = formatOriginal.format(dateOriginal)
        }

    }
}