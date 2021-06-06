package com.example.sirius.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.sirius.R
import com.example.sirius.core.di.factory.ViewModelFactory
import com.example.sirius.databinding.FragmentMainBinding
import com.example.sirius.domain.model.data.DeveloperNote
import com.example.sirius.domain.model.data.Result
import com.example.sirius.util.setVisibility
import com.example.sirius.util.showAlert
import com.example.sirius.util.showError
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class MainFragment : DaggerFragment(R.layout.fragment_main) {
    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: MainViewModel by viewModels { factory }

    private val binding: FragmentMainBinding by viewBinding()

    private var currentNote: DeveloperNote? = null

    private var bottomSheetListener: OnFragmentInteractionListener? = null

    private val notesObserver = Observer<Result<DeveloperNote?>> { result ->
        when (result) {
            is Result.Success -> result.data?.let {
                handleSuccessLoadingNotes(it)
            }
            is Result.Failure -> handleFailedLoadingNotes(result.msg)
            is Result.Loading -> showLoading(true)
        }
    }

    private val previousEnableObserver = Observer<Boolean> {
        binding.previousPost.setVisibility(it)
    }

    private val nextStepListener = View.OnClickListener {
        viewModel.loadNotes(true)
    }

    private val previousStepListener = View.OnClickListener {
        viewModel.loadNotes(false)
    }

    private val clearDataListener = View.OnClickListener {
        showAlert(
            message = getString(R.string.clear_data_msg),
            positiveAction = getString(R.string.button_ok) to { viewModel.clearData() },
            negativeAction = getString(R.string.button_cancel) to {},
            cancelable = true
        )
    }

    private val infoClickListener = View.OnClickListener {
        currentNote?.let {
            val bundle = Bundle().apply { putSerializable(BUNDLE_NOTE_KEY, currentNote) }
            bottomSheetListener?.sendNote(bundle)
        } ?: handleFailedLoadingNotes(null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initEventListener()
    }

    private fun initObservers() = with(viewModel) {
        notes.observe(viewLifecycleOwner, notesObserver)
        buttonEnabled.observe(viewLifecycleOwner, previousEnableObserver)
    }

    private fun initEventListener() = with(binding) {
        nextPost.setOnClickListener(nextStepListener)
        previousPost.setOnClickListener(previousStepListener)
        info.setOnClickListener(infoClickListener)
        garbage.setOnClickListener(clearDataListener)
        bottomSheetListener = requireContext() as OnFragmentInteractionListener
    }

    private fun handleSuccessLoadingNotes(developerNote: DeveloperNote) = with(binding) {
        Glide.with(requireContext())
            .asGif()
            .apply(getRequestOptions())
            .load(developerNote.gifUrl)
            .into(image)

        description.text = developerNote.description
        authorName.text = developerNote.authorName
        currentNote = developerNote
        showLoading(false)
    }

    private fun handleFailedLoadingNotes(msg: String?) {
        val defaultMsg = getString(R.string.default_msg_error)
        val message = msg ?: defaultMsg
        binding.description.text = defaultMsg
        showError(message) { viewModel.loadNotes() }
    }

    private fun showLoading(isLoading: Boolean) = with(binding) {
        image.setVisibility(isLoading.not())
        info.setVisibility(isLoading.not())
    }

    private fun getRequestOptions() = RequestOptions().apply {
        val corners = getCornerRadius()
        transforms(
            CenterCrop(),
            GranularRoundedCorners(
                corners, corners, 0.0F, 0.0F
            )
        )
    }

    private fun getCornerRadius(): Float {
        val density = resources.displayMetrics.density
        return CORNER_RADIUS * density
    }

    interface OnFragmentInteractionListener {
        fun sendNote(bundle: Bundle)
    }

    companion object {
        private const val CORNER_RADIUS = 16
        const val BUNDLE_NOTE_KEY = "note_key"
    }
}