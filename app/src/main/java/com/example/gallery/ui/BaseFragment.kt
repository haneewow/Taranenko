package com.example.gallery.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.gallery.R
import com.example.gallery.domain.model.data.DeveloperNote
import com.example.gallery.util.showError
import dagger.android.support.DaggerFragment

open class BaseFragment(layoutId: Int) : DaggerFragment(layoutId) {
    protected var currentNote: DeveloperNote? = null

    private var bottomSheetListener: OnFragmentInteractionListener? = null

    protected val circularProgressDrawable by lazy {
        CircularProgressDrawable(requireContext()).apply {
            strokeWidth = 10f
            centerRadius = 100f
            start()
        }
    }

    protected val infoClickListener = View.OnClickListener {
        currentNote?.let {
            val bundle = Bundle().apply { putSerializable(BUNDLE_NOTE_KEY, currentNote) }
            bottomSheetListener?.sendNote(bundle)
        } ?: handleFailedLoadingNotes(null, null)
    }

    protected fun handleFailedLoadingNotes(msg: String?, view: View?) {
        val defaultMsg = getString(R.string.default_msg_error)
        val message = msg ?: defaultMsg

        view?.setBackgroundDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.error_placeholder
            )
        )
        showError(message)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        bottomSheetListener = requireContext() as OnFragmentInteractionListener
    }

    interface OnFragmentInteractionListener {
        fun sendNote(bundle: Bundle)
    }

    companion object {
        const val BUNDLE_NOTE_KEY = "note_key"
    }
}