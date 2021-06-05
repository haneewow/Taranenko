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

    private var number = 1

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
        number++
        viewModel.loadNotes(number)
    }

    private val previousStepListener = View.OnClickListener {
        number--
        viewModel.loadNotes(number)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initEventListener()
        viewModel.loadNotes(number)
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
    }

    private fun handleSuccessLoadingNotes(developerNote: DeveloperNote) = with(binding) {
        Glide.with(requireContext())
            .asGif()
            .load(developerNote.gifUrl)
            .centerCrop()
            .into(image)

        description.text = developerNote.description
        showLoading(false)
    }

    private fun handleFailedLoadingNotes(msg: String?, code: Int?) {
        val message = msg ?: getString(R.string.default_msg_error)
        showError(message) { viewModel.loadNotes(number++) }
    }

    private fun showLoading(isLoading: Boolean) = with(binding) {
        progressBar.setVisibility(isLoading)
        image.setVisibility(isLoading.not())
    }
}