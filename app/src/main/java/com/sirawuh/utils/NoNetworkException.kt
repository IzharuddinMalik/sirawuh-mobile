package com.sirawuh.utils

import java.io.IOException

class NoNetworkException : IOException() {
    override val message: String
        get() = "Tidak Ada Koneksi Internet"
}