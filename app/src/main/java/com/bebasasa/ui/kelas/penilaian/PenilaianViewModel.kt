package com.bebasasa.ui.kelas.penilaian

import android.annotation.SuppressLint
import androidx.lifecycle.viewModelScope
import com.bebasasa.core.base.BaseViewModel
import com.bebasasa.data.domain.DataPenilaianResp
import com.bebasasa.data.domain.DataSiswaResp
import com.bebasasa.data.domain.DetailPenilaianResponse
import com.bebasasa.data.domain.GeneralResponse
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
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class PenilaianViewModel @Inject constructor(
    private val penilaianRepository: PenilaianRepository
) : BaseViewModel() {

    private val _listSiswa9a = MutableStateFlow<List<DataSiswaResp>?>(null)
    val listSiswa9a: StateFlow<List<DataSiswaResp>?> = _listSiswa9a

    private val _listSiswa9b = MutableStateFlow<List<DataSiswaResp>?>(null)
    val listSiswa9b: StateFlow<List<DataSiswaResp>?> = _listSiswa9b

    private val _tambahPenilaianResp = MutableStateFlow<GeneralResponse?>(null)
    val tambahPenilaianResp: StateFlow<GeneralResponse?> = _tambahPenilaianResp

    private val _listPenilaian9a = MutableStateFlow<List<DataPenilaianResp>?>(null)
    val listPenilaian9a: StateFlow<List<DataPenilaianResp>?> = _listPenilaian9a

    private val _listPenilaian9b = MutableStateFlow<List<DataPenilaianResp>?>(null)
    val listPenilaian9b: StateFlow<List<DataPenilaianResp>?> = _listPenilaian9b

    private val _hapusPenilaianResp = MutableStateFlow<GeneralResponse?>(null)
    val hapusPenilaianResp: StateFlow<GeneralResponse?> = _hapusPenilaianResp

    private val _detailPenilaian9a = MutableStateFlow<DetailPenilaianResponse?>(null)
    val detailPenilaian9a: StateFlow<DetailPenilaianResponse?> = _detailPenilaian9a

    private val _detailPenilaian9b = MutableStateFlow<DetailPenilaianResponse?>(null)
    val detailPenilaian9b: StateFlow<DetailPenilaianResponse?> = _detailPenilaian9b

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error

    private val _judulPenilaianValid = MutableStateFlow<Boolean?>(null)
    val judulPenilaianValid: StateFlow<Boolean?> = _judulPenilaianValid

    private val _isButtonEnable = MutableStateFlow<Boolean?>(null)
    val isButtonEnable: StateFlow<Boolean?> = _isButtonEnable

    var arrNilaiList = ArrayList<String>()
    var arrNipnisList = ArrayList<String>()
    var nilaiList = MutableStateFlow<ArrayList<String>?>(null)
    var nipnisList = MutableStateFlow<ArrayList<String>?>(null)
    var judulPenilaian = MutableStateFlow("")
    private val _tanggalPenilaianOriginal = MutableStateFlow("")
    val tanggalPenilaianOriginal: StateFlow<String> = _tanggalPenilaianOriginal
    private val _tanggalPenilaianConvert = MutableStateFlow("")
    val tanggalPenilaianConvert: StateFlow<String> = _tanggalPenilaianConvert
    private val _kelasSend = MutableStateFlow("")
    val kelasSend: StateFlow<String> = _kelasSend

    fun initState() {
        viewModelScope.launch {
            combine(
                judulPenilaian
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
        fun getListPenilaian9a() {
            viewModelScope.launch {
                penilaianRepository.getPenilaian9a()
                    .onStart { showTransparentLoading() }
                    .onCompletion { dismissTransparentLoading() }
                    .collect { result ->
                        when (result) {
                            is Result.Success -> {
                                _listPenilaian9a.value = result.data?.data
                            }

                            is Result.Error -> {
                                _error.update { result.getErrorMessage.orEmpty() }
                            }
                        }
                    }
            }
        }

        fun getListPenilaian9b() {
            viewModelScope.launch {
                penilaianRepository.getPenilaian9b()
                    .onStart { showTransparentLoading() }
                    .onCompletion { dismissTransparentLoading() }
                    .collect { result ->
                        when (result) {
                            is Result.Success -> {
                                _listPenilaian9b.value = result.data?.data
                            }

                            is Result.Error -> {
                                _error.update { result.getErrorMessage.orEmpty() }
                            }
                        }
                    }
            }
        }

        fun getSiswa9a() {
            viewModelScope.launch {
                penilaianRepository.getSiswa9a()
                    .onStart { showTransparentLoading() }
                    .onCompletion { dismissTransparentLoading() }
                    .collect { result ->
                        when (result) {
                            is Result.Success -> {
                                _listSiswa9a.value = result.data?.data
                            }

                            is Result.Error -> {
                                _error.update { result.getErrorMessage.orEmpty() }
                            }
                        }
                    }
            }
        }

        fun getSiswa9b() {
            viewModelScope.launch {
                penilaianRepository.getSiswa9b()
                    .onStart { showTransparentLoading() }
                    .onCompletion { dismissTransparentLoading() }
                    .collect { result ->
                        when (result) {
                            is Result.Success -> {
                                _listSiswa9b.value = result.data?.data
                            }

                            is Result.Error -> {
                                _error.update { result.getErrorMessage.orEmpty() }
                            }
                        }
                    }
            }
        }

        fun tambahPenilaian() {
            val arrData = JSONArray()
            var itemObj: JSONObject

            try {

                for (i in 0 until nilaiList.value!!.size) {
                    itemObj = JSONObject()
                    try {
                        itemObj.put("judulpenilaian", judulPenilaian.value)
                        itemObj.put("nipnis", nipnisList.value!![i])
                        itemObj.put("nilai", nilaiList.value!![i])
                        itemObj.put("tanggalpenilaian", tanggalPenilaianOriginal.value)
                        itemObj.put("kelas", kelasSend.value)
                        arrData.put(itemObj)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

            } catch (e: JSONException) {
                e.printStackTrace()
            }

            val requestBody: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("nilailist", arrData.toString())
                .build()

            handleTambahPenilaian(requestBody,
                onSuccess = {
                    _tambahPenilaianResp.value = it
                },
                onError = {
                    _error.update { it }
                })
        }

        private fun handleTambahPenilaian(
            body: RequestBody,
            onSuccess: (GeneralResponse?) -> Unit,
            onError: (String) -> Unit
        ) {

            viewModelScope.launch {
                penilaianRepository.tambahPenilaian(body)
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

        fun reqHapusPenilaian() {
            val requestBody: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("kelas", kelasSend.value)
                .addFormDataPart("tanggalpenilaian", tanggalPenilaianOriginal.value)
                .build()

            handleHapusPenilaian(requestBody,
                onSuccess = {
                    _hapusPenilaianResp.value = it
                },
                onError = {
                    _error.update { it }
                })
        }

        private fun handleHapusPenilaian(
            body: RequestBody,
            onSuccess: (GeneralResponse?) -> Unit,
            onError: (String) -> Unit
        ) {
            viewModelScope.launch {
                penilaianRepository.hapusPenilaian(body)
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

        fun getDetailPenilaian9a() {
            val requestBody: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("tanggal", tanggalPenilaianOriginal.value)
                .build()

            handleDetailPenilaian9a(
                requestBody,
                onSuccess = {
                    _detailPenilaian9a.value = it
                },
                onError = {
                    _error.update { it }
                }
            )
        }

        private fun handleDetailPenilaian9a(
            body: RequestBody,
            onSuccess: (DetailPenilaianResponse?) -> Unit,
            onError: (String) -> Unit
        ) {
            viewModelScope.launch {
                penilaianRepository.detailPenilaian9a(body)
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

        fun getDetailPenilaian9b() {
            val requestBody: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("tanggal", tanggalPenilaianOriginal.value)
                .build()

            handleDetailPenilaian9b(
                requestBody,
                onSuccess = {
                    _detailPenilaian9b.value = it
                },
                onError = {
                    _error.update { it }
                }
            )
        }

        private fun handleDetailPenilaian9b(
            body: RequestBody,
            onSuccess: (DetailPenilaianResponse?) -> Unit,
            onError: (String) -> Unit
        ) {
            viewModelScope.launch {
                penilaianRepository.detailPenilaian9b(body)
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
    }

    inner class StateHandler {
        fun setNilaiList(nilai: String) {
            arrNilaiList.add(nilai)
            nilaiList.update { arrNilaiList }
        }

        fun setNipNisList(nipnis: String) {
            arrNipnisList.add(nipnis)
            nipnisList.update { arrNipnisList }
        }

        fun setJudulPenilaian(judul: String) {
            judulPenilaian.update { judul }
            val isValid = judul.run {
                isEmpty()
            }
            _judulPenilaianValid.update { isValid }
        }

        fun setKelas(kelas: String) {
            _kelasSend.update { kelas }
        }

        fun setTanggalHapus(tanggal: String) {
            _tanggalPenilaianOriginal.update { tanggal }
        }

        @SuppressLint("SimpleDateFormat")
        fun convertDate(time: Long) {
            val date = Date(time)
            val format = SimpleDateFormat("dd-MM-yyyy")
            _tanggalPenilaianConvert.value = format.format(date)

            val dateOriginal = Date(time)
            val formatOriginal = SimpleDateFormat("yyyy-MM-dd")
            _tanggalPenilaianOriginal.value = formatOriginal.format(dateOriginal)
        }
    }
}