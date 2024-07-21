package com.bebasasa.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.clearFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.bebasasa.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.internal.managers.ViewComponentManager

fun Fragment.navigateToUri(
    @StringRes resId: Int,
    tag: String = getString(R.string.route_host),
    param: String = ""
) {
    kotlin.runCatching {
        val uri = getString(resId).substringBefore("/{")
        val destination = if (param.isNotEmpty()) "$uri/$param".toUri() else uri.toUri()
        val navOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in)
            .setPopEnterAnim(R.anim.fade_in)
            .setExitAnim(R.anim.fade_out)
            .setPopExitAnim(R.anim.slide_out)
            .build()
        val request = NavDeepLinkRequest.Builder
            .fromUri(destination).setUri(destination)
            .build()
        getActiveController(tag).navigate(request = request, navOptions)
    }.onFailure {
        requireContext().toast(R.string.page_not_found)
    }
}

fun Fragment.navigateToUri(deeplinkUri: String, tag: String = getString(R.string.route_host)) {
    kotlin.runCatching {
        val uri = deeplinkUri.toUri()
        val navOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in)
            .setPopEnterAnim(R.anim.fade_in)
            .setExitAnim(R.anim.fade_out)
            .setPopExitAnim(R.anim.slide_out)
            .build()
        val request = NavDeepLinkRequest.Builder
            .fromUri(uri).setUri(uri)
            .build()
        getActiveController(tag).navigate(request, navOptions)
    }.onFailure {
        requireContext().toast(R.string.page_not_found)
    }

}

fun Fragment.navigateInclusivelyToUri(
    @StringRes resId: Int,
    tag: String = getString(R.string.route_host)
) {
    println("cek panggil navigation")
    kotlin.runCatching {
        val uri = getString(resId).toUri()
        val navOptions = NavOptions.Builder()
            .setPopUpTo(destinationId = 0, inclusive = true, saveState = false)
            .setEnterAnim(R.anim.slide_in)
            .setPopEnterAnim(R.anim.fade_in)
            .setExitAnim(R.anim.fade_out)
            .setPopExitAnim(R.anim.slide_out)
            .build()
        val request = NavDeepLinkRequest.Builder
            .fromUri(uri)
            .build()
        getActiveController(tag).navigate(request, navOptions)
    }.onFailure {
        requireContext().toast(R.string.page_not_found)
    }
}

private fun Fragment.getActiveController(tag: String): NavController {
    val hostFragment =
        activity?.supportFragmentManager?.findFragmentByTag(tag) as NavHostFragment
    return hostFragment.navController
}


fun Context.navigateToUri(
    @StringRes resId: Int,
    tag: String = getString(R.string.route_host)
) {
    kotlin.runCatching {
        val uri = getString(resId).toUri()
        val request = NavDeepLinkRequest.Builder
            .fromUri(uri)
            .build()
        val navOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in)
            .setPopEnterAnim(R.anim.fade_in)
            .setExitAnim(R.anim.fade_out)
            .setPopExitAnim(R.anim.slide_out)
            .build()
        this.getActiveController(tag).navigate(request, navOptions)
    }
}

fun Fragment.fragmentResultFrom(key: String, result: (Bundle) -> Unit) {
    setFragmentResultListener(key) { _, bundle ->
        result.invoke(bundle)
        clearFragmentResult(key)
    }
}

private fun Context.getActiveController(tag: String = getString(R.string.route_host)): NavController {
    val currentContext =
        if (this is ViewComponentManager.FragmentContextWrapper) (baseContext as AppCompatActivity)
        else (this as AppCompatActivity)
    val navHost = currentContext.supportFragmentManager.findFragmentByTag(tag) as NavHostFragment
    return navHost.navController
}

fun Context.navigateUp(tag: String = getString(R.string.route_host)) {
    kotlin.runCatching {
        this.getActiveController(tag).navigateUp()
    }
}


fun Fragment.navigateInclusivelyToAction(
    @IdRes resId: Int,
    tag: String = getString(R.string.route_host)
) {
    val navOptions = NavOptions.Builder()
        .setPopUpTo(destinationId = 0, inclusive = true, saveState = false)
        .build()
    getActiveController(tag).navigate(resId = resId, args = null, navOptions = navOptions)
}

fun Context.getNameController(tag: String = getString(R.string.route_host)): String {
    val currentContext =
        if (this is ViewComponentManager.FragmentContextWrapper) (baseContext as AppCompatActivity)
        else (this as AppCompatActivity)
    val navHost = currentContext.supportFragmentManager.findFragmentByTag(tag) as NavHostFragment
    return navHost.navController.currentDestination?.label.toString()
}

fun Fragment.navigateToAction(
    @IdRes resId: Int,
    args: Bundle? = null,
    navOptions: NavOptions? = null,
    tag: String = getString(R.string.route_host)
) {
    getActiveController(tag).navigate(resId, args, navOptions)
}

fun Fragment.openBottomSheetFragment(fragment: BottomSheetDialogFragment, bundle: Bundle) {
    fragment.arguments = bundle
    fragment.isCancelable = true
    fragment.show(
        requireActivity().supportFragmentManager,
        fragment.tag
    )
}

fun Context.openBottomSheetFragment(fragment: BottomSheetDialogFragment, bundle: Bundle) {
    kotlin.runCatching {
        val currentContext =
            if (this is ViewComponentManager.FragmentContextWrapper) (baseContext as AppCompatActivity)
            else (this as AppCompatActivity)
        val navHost = currentContext.supportFragmentManager
        fragment.arguments = bundle
        fragment.isCancelable = true
        fragment.show(
            navHost,
            fragment.tag
        )
    }.onFailure {
        toast(R.string.page_not_found)
    }
}

fun Context.showMessageAndLogout() {
    Handler(Looper.getMainLooper()).post {
        Toast.makeText(this, R.string.message_unauthorized, Toast.LENGTH_LONG).show()
        restartApp()
    }
}

fun Context.restartApp() {
    kotlin.runCatching {
        startActivity(Intent(Intent.ACTION_VIEW).apply {
            val route = R.string.root_route

            data = getString(route).toUri()
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
    }
}