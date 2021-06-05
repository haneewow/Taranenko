package com.example.sirius.util

import android.view.View
import androidx.fragment.app.Fragment
import com.example.sirius.R
import com.google.android.material.snackbar.Snackbar

fun View.setVisibility(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun Fragment.showError(message: String, action: () -> Unit) = view?.let {
    Snackbar
        .make(it, message, Snackbar.LENGTH_INDEFINITE)
        .setAction(getString(R.string.default_action)) { action() }
        .setActionTextColor(requireContext().getColor(R.color.secondaryVariant))
        .show()
}
