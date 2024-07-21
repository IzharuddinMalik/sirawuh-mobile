package com.bebasasa.ui.kelas.tanyajawab

import androidx.lifecycle.viewModelScope
import com.bebasasa.core.base.BaseViewModel
import com.bebasasa.data.domain.ChatForumResponse
import com.bebasasa.data.domain.DataListForumResp
import com.bebasasa.data.domain.GeneralResponse
import com.bebasasa.data.source.local.PreferenceHelper
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
class TanyaJawabViewModel @Inject constructor(
    private val tanyaJawabRepository: TanyaJawabRepository,
    private val preferenceHelper: PreferenceHelper
) : BaseViewModel() {

    private val _buatTanyaJawabResp = MutableStateFlow<GeneralResponse?>(null)
    val buatTanyaJawabResp: StateFlow<GeneralResponse?> = _buatTanyaJawabResp

    private val _listForumResp = MutableStateFlow<List<DataListForumResp>?>(null)
    val listForumResp: StateFlow<List<DataListForumResp>?> = _listForumResp

    private val _chatForumResp = MutableStateFlow<GeneralResponse?>(null)
    val chatForumResp: StateFlow<GeneralResponse?> = _chatForumResp

    private val _detailForumResp = MutableStateFlow<ChatForumResponse?>(null)
    val detaiForumResp: StateFlow<ChatForumResponse?> = _detailForumResp

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error

    private val _isButtonEnable = MutableStateFlow<Boolean?>(false)
    val isButtonEnable: StateFlow<Boolean?> = _isButtonEnable

    private val _judulPertanyaanValid = MutableStateFlow<Boolean?>(false)
    val judulPertanyaanValid: StateFlow<Boolean?> = _judulPertanyaanValid

    private val _messageValid = MutableStateFlow<Boolean?>(false)
    val messageValid: StateFlow<Boolean?> = _messageValid

    private val _judulPertanyaan = MutableStateFlow("")
    val judulPertanyaan: StateFlow<String> = _judulPertanyaan

    private val _messageForum = MutableStateFlow("")
    val messageForum: StateFlow<String> = _messageForum

    private val _idTanyaJawab = MutableStateFlow("")
    val idTanyaJawab: StateFlow<String> = _idTanyaJawab

    fun initState() {
        viewModelScope.launch {
            combine(
                judulPertanyaan
            ) { field ->
                _isButtonEnable.update {
                    areFieldsNotEmpty(field)
                }
            }.collect()
        }
    }

    private fun areFieldsNotEmpty(fieldsArray: Array<String>): Boolean {
        return fieldsArray.all { it.isNotEmpty() }
    }

    inner class RequestHandler {
        fun getListForum() {
            viewModelScope.launch {
                tanyaJawabRepository.listForum()
                    .onStart { showTransparentLoading() }
                    .onCompletion { dismissTransparentLoading() }
                    .collect { result ->
                        when (result) {
                            is Result.Success -> {
                                _listForumResp.value = result.data?.data
                            }

                            is Result.Error -> {
                                _error.update { result.getErrorMessage.orEmpty() }
                            }
                        }
                    }
            }
        }

        fun buatTanyaJawab() {
            val requestBody: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("iduser", preferenceHelper.getIdUserSession()!!)
                .addFormDataPart("judulpertanyaan", judulPertanyaan.value)
                .build()

            handleTanyaJawab(requestBody,
                onSuccess = {
                    _buatTanyaJawabResp.value = it
                },
                onError = {
                    _error.update { it }
                })
        }

        private fun handleTanyaJawab(
            body: RequestBody,
            onSuccess: (GeneralResponse?) -> Unit,
            onError: (String) -> Unit
        ) {
            viewModelScope.launch {
                tanyaJawabRepository.buatTanyaJawab(body)
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

        fun kirimPesanForum() {
            val requestBody: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("idtanyajawab", idTanyaJawab.value)
                .addFormDataPart("iduser", preferenceHelper.getIdUserSession()!!)
                .addFormDataPart("message", messageForum.value)
                .build()

            handleKirimPesanForum(requestBody,
                onSuccess = {
                    _chatForumResp.value = it
                },
                onError = {
                    _error.update { it }
                })
        }

        private fun handleKirimPesanForum(
            body: RequestBody,
            onSuccess: (GeneralResponse?) -> Unit,
            onError: (String) -> Unit
        ) {
            viewModelScope.launch {
                tanyaJawabRepository.chatForum(body)
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

        fun detailForum() {
            val requestBody: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("idtanyajawab", idTanyaJawab.value)
                .build()

            viewModelScope.launch {
                tanyaJawabRepository.detailForum(requestBody)
                    .onStart { showTransparentLoading() }
                    .onCompletion { dismissTransparentLoading() }
                    .collect { result ->
                        when (result) {
                            is Result.Success -> {
                                _detailForumResp.value = result.data
                            }

                            is Result.Error -> {
                                _error.update { result.getErrorMessage.orEmpty() }
                            }
                        }
                    }
            }
        }
    }

    inner class StateHandler {

        fun setJudulPertanyaan(value: String) {
            _judulPertanyaan.update { value }
            val isValid = value.run {
                isEmpty()
            }
            _judulPertanyaanValid.update { isValid }
        }

        fun setMessageForum(value: String) {
            _messageForum.update { value }
            val isValid = value.run {
                isEmpty()
            }
            _messageValid.update { isValid }
        }

        fun setIdTanyaJawab(value: String) {
            _idTanyaJawab.update { value }
        }
    }
}