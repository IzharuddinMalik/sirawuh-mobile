package com.bebasasa.utils

import java.io.IOException

class NoNetworkException : IOException() {
    override val message: String
        get() = "Tidak Ada Koneksi Internet"
}