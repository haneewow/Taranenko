package com.example.gallery.ui.pages.best

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.gallery.R
import com.example.gallery.core.di.factory.ViewModelFactory
import com.example.gallery.databinding.FragmentBestBinding
import com.example.gallery.domain.model.data.DeveloperNote
import com.example.gallery.domain.model.data.Result
import com.example.gallery.ui.BaseFragment
import com.example.gallery.ui.adapter.Category
import com.example.gallery.util.setVisibility
import com.example.gallery.util.showAlert
import com.example.gallery.util.showError
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class BestFragment : BaseFragment(R.layout.fragment_best) {
    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: BestViewModel by viewModels { factory }

    private val binding: FragmentBestBinding by viewBinding()

    private val notesObserver = Observer<Result<DeveloperNote?>> {
        it.handleResult(
            successDelegate = ::handleSuccessLoadingNotes,
            failureDelegate = { handleFailedLoadingNotes(it, binding.image, binding.errorHolder) },
            loadingDelegate = { showLoading(true) }
        )
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

    private val glideListener = object : RequestListener<GifDrawable?> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<GifDrawable?>?,
            isFirstResource: Boolean
        ): Boolean {
            handleFailedLoadingNotes(
                e?.message ?: getString(R.string.default_msg_error),
                binding.image
            )
            return false
        }

        override fun onResourceReady(
            resource: GifDrawable?,
            model: Any?,
            target: Target<GifDrawable?>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            return false
        }

    }

    private val clearDataListener = View.OnClickListener {
        showAlert(
            message = getString(R.string.clear_data_msg),
            positiveAction = getString(R.string.button_ok) to { viewModel.clearData(Category.TOP) },
            negativeAction = getString(R.string.button_cancel) to {},
            cancelable = true
        )
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
    }

    private fun handleSuccessLoadingNotes(developerNote: DeveloperNote?) = with(binding) {
        developerNote ?: return@with
        errorHolder.setVisibility(false)
        Glide.with(requireContext())
            .asGif()
            .centerCrop()
            .placeholder(circularProgressDrawable)
            .load(developerNote.gifUrl)
            .addListener(glideListener)
            .into(image)

        description.text = developerNote.description
        authorName.text = developerNote.authorName
        currentNote = developerNote
        showLoading(false)
    }

    private fun showLoading(isLoading: Boolean) = with(binding) {
        info.setVisibility(isLoading.not())
    }
}