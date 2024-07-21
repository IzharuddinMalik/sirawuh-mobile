package com.bebasasa.data.source.local

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.bebasasa.data.domain.DataLoginResp
import com.bebasasa.utils.KeyPrefConst

class PreferenceHelper(
    context: Context
) {
    // Step 1: Create or retrieve the Master Key for encryption/decryption
    private var masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    private val encryptedPreference = EncryptedSharedPreferences.create(
        "SecurePreferences",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun putBoolean(key: String, value: Boolean) {
        encryptedPreference.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return encryptedPreference.getBoolean(key, defaultValue)
    }

    fun clearSessionUserLoginResponse(): Boolean {
        val editor = encryptedPreference.edit()
        return editor.run {
            remove(KeyPrefConst.IS_LOGGED_IN).commit()
        }
    }

    fun getNipNisSession(): String? {
        return encryptedPreference.getString(KeyPrefConst.NIPNIS, null)
    }

    fun saveNipNisSession(idUser: String) {
        kotlin.runCatching {
            encryptedPreference.edit().putString(KeyPrefConst.NIPNIS, idUser).apply()
        }.onFailure {
            error(it)
        }
    }

    fun getString(key: String, defaultValue: String? = ""): String? {
        return encryptedPreference.getString(key, defaultValue)
    }
}