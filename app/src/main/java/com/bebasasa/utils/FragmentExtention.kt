package com.bebasasa.utils

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun Fragment.refreshPreviousPage() {
    kotlin.runCatching {
        requireActivity().supportFragmentManager.setFragmentResult(
            Constants.REFRESH_KEY,
            bundleOf(Constants.REFRESH_ARGUMENT to true)
        )
    }
}

fun Fragment.refreshPreviousPage(key: String) {
    kotlin.runCatching {
        requireActivity().supportFragmentManager.setFragmentResult(
            key,
            bundleOf(key to true)
        )
    }

}

fun Fragment.observeRefresh(onRefresh: () -> Unit) {
    kotlin.runCatching {
        requireActivity().supportFragmentManager.setFragmentResultListener(
            Constants.REFRESH_KEY,
            viewLifecycleOwner
        ) { key, args ->
            if (key == Constants.REFRESH_KEY) {
                val shouldRefresh = args.getBoolean(Constants.REFRESH_ARGUMENT)
                if (shouldRefresh) {
                    onRefresh()
                    args.remove(Constants.REFRESH_ARGUMENT)
                }
            }
        }
    }
}

inline fun <T> Fragment.observe(data: Flow<T>, crossinline onChange: (T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            data.collect {
                onChange(it)
            }
        }
    }
}

fun Fragment.observeRefresh(keyListen: String, onRefresh: () -> Unit) {
    kotlin.runCatching {
        requireActivity().supportFragmentManager.setFragmentResultListener(
            keyListen,
            viewLifecycleOwner
        ) { key, args ->
            if (key == keyListen) {
                val shouldRefresh = args.getBoolean(keyListen)
                if (shouldRefresh) onRefresh()
            }
        }
    }
}

fun Fragment.getStringByKey(key: String, removeAfterRead: Boolean = false): String {
    val result = arguments?.getString(key, "") ?: ""
    if (removeAfterRead) arguments?.remove(key)
    return result
}
