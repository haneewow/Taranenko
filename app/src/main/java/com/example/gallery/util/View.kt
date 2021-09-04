package com.example.gallery.util

import android.app.AlertDialog
import android.view.View
import androidx.fragment.app.Fragment
import com.example.gallery.R
import com.google.android.material.snackbar.Snackbar

fun View.setVisibility(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun Fragment.showError(message: String) = view?.let {
    Snackbar
        .make(it, message, Snackbar.LENGTH_LONG)
        .setActionTextColor(requireContext().getColor(R.color.primary))
        .show()
}

fun Fragment.showAlert(title: String? = null,
                       message: String? = null,
                       positiveAction: Pair<String?, () -> Unit?> = Pair(getString(R.string.button_ok), {}),
                       negativeAction: Pair<String?, () -> Unit?>? = null,
                       neutralAction: Pair<String, () -> Unit>? = null,
                       cancelable: Boolean? = true) {
    view?.run {
        @Suppress("NAME_SHADOWING")
        context?.let { context ->
            val message =
                if (message !== null && message.isNotEmpty()) message
                else context.getString(R.string.default_msg_error)
            val cancelable = cancelable ?: true
            AlertDialog.Builder(context).apply {
                positiveAction.run { setPositiveButton(first) { _, _ -> second() } }
                negativeAction?.run { setNegativeButton(first) { _, _ -> second() } }
                neutralAction?.run { setNeutralButton(first) { _, _ -> second() } }
                setTitle(title)
                setMessage(message)
                setCancelable(cancelable)
                show()
            }
        }
    }
}
