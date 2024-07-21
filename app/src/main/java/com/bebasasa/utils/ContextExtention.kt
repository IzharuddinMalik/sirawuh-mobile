package com.bebasasa.utils

import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.NumberPicker
import android.widget.Toast
import androidx.annotation.StringRes
import com.google.android.gms.common.util.ClientLibraryUtils
import java.util.Calendar

fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}

fun Context.toast(@StringRes resId: Int, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, resId, length).show()
}

fun Context.showKeyboard(view: View) {
    view.requestFocus()
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}

fun Context.hideSoftKeyboard(view: View) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.showYearPickerDialog(onYearSelected: (Int) -> Unit) {
    val calendar = Calendar.getInstance()
    val currentYear = calendar[Calendar.YEAR]

    val numberPicker = NumberPicker(this)
    numberPicker.minValue = 1980 // Set the minimum year you want to display
    numberPicker.maxValue = currentYear // Set the maximum year to the current year
    numberPicker.value = currentYear

    // Prevent editing of the year in the NumberPicker
    numberPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

    val dialog = AlertDialog.Builder(this)
        .setTitle("Tahun Tanam Mayoritas")
        .setView(numberPicker)
        .setPositiveButton("OK") { _, _ ->
            val selectedYear = numberPicker.value
            if (selectedYear <= currentYear) {
                onYearSelected(selectedYear)
            } else {
                // Notify the user that they can't select a year beyond the current year
                Toast.makeText(
                    this,
                    "Tidak dapat memilih tahun di luar tahun berjalan",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        .setNegativeButton("Batal", null)
        .create()

    dialog.show()
}

fun Context.isPackageInstalled(packageName: String): Boolean {
    return kotlin.runCatching {
        val packageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager?.getPackageInfo(
                packageName,
                PackageManager.PackageInfoFlags.of(0)
            )
        } else {
            ClientLibraryUtils.getPackageInfo(
                this,
                packageName
            )
        }
        packageInfo != null
    }.getOrDefault(false)
}

fun View.hideKeyboard() {
    kotlin.runCatching {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}
