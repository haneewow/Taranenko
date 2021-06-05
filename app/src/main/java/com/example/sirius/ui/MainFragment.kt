package com.example.sirius.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.sirius.R
import com.example.sirius.core.di.factory.ViewModelFactory
import com.example.sirius.databinding.FragmentMainBinding
import com.example.sirius.domain.model.data.DeveloperNote
import com.example.sirius.domain.model.data.Result
import com.example.sirius.util.setVisibility
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
            is Result.Failure -> handleFailedLoadingNotes(result.msg, result.code)
            is Result.Loading -> showLoading(true)
        }
    }

    private val previousEnableObserver = Observer<Boolean> {
        binding.previousPost.isEnabled = it
    }

    private val nextStepListener = View.OnClickListener {
        viewModel.loadNotes(true)
    }

    private val previousStepListener = View.OnClickListener {
        viewModel.loadNotes(false)
    }

    private val infoClickListener = View.OnClickListener {
        val bundle = Bundle().apply {
            putSerializable(BUNDLE_NOTE_KEY, currentNote)
        }

        bottomSheetListener?.sendNote(bundle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initEventListener()
    }

    override fun onDestroy() {
        viewModel.clearData()
        super.onDestroy()
    }

    private fun initObservers() = with(viewModel) {
        notes.observe(viewLifecycleOwner, notesObserver)
        buttonEnabled.observe(viewLifecycleOwner, previousEnableObserver)
    }

    private fun initEventListener() = with(binding) {
        nextPost.setOnClickListener(nextStepListener)
        previousPost.setOnClickListener(previousStepListener)
        info.setOnClickListener(infoClickListener)
        bottomSheetListener = requireContext() as OnFragmentInteractionListener
    }

    private fun handleSuccessLoadingNotes(developerNote: DeveloperNote) = with(binding) {
        Glide.with(requireContext())
            .asGif()
            .load(developerNote.gifUrl)
            .centerCrop()
            .into(image)

        description.text = developerNote.description
        currentNote = developerNote
        showLoading(false)
    }

    private fun handleFailedLoadingNotes(msg: String?, code: Int?) {
        val message = msg ?: getString(R.string.default_msg_error)
        showError(message) { viewModel.loadNotes() }
    }

    private fun showLoading(isLoading: Boolean) = with(binding) {
        progressBar.setVisibility(isLoading)
        image.setVisibility(isLoading.not())
        info.setVisibility(isLoading.not())
    }

    companion object {
        const val BUNDLE_NOTE_KEY = "note_key"
    }

    fun interface OnFragmentInteractionListener {
        fun sendNote(bundle: Bundle)
    }
}