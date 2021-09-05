package com.example.sirius.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.sirius.R
import com.example.sirius.core.di.factory.ViewModelFactory
import com.example.sirius.databinding.ActivityMainBinding
import com.example.sirius.domain.model.data.DeveloperNote
import com.example.sirius.domain.model.data.DeveloperStack
import com.example.sirius.domain.model.data.Result
import com.example.sirius.util.setVisibility
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(R.layout.activity_main) {
    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: MainViewModel by viewModels { factory }

    private val binding: ActivityMainBinding by viewBinding()

    private val notesObserver = Observer<DeveloperStack?> { stack ->
        stack ?: return@Observer
        handleSuccessLoadingNotes(stack)
    }

    private val circularProgressDrawable by lazy {
        CircularProgressDrawable(this).apply {
            strokeWidth = 10f
            centerRadius = 50f
            start()
        }
    }

    private val transitionListener = object : TransitionAdapter() {
        override fun onTransitionCompleted(motionLayout: MotionLayout, currentId: Int) {
            when (currentId) {
                R.id.offScreenNext -> {
                    binding.motionLayout.progress = 0f
                    binding.motionLayout.setTransition(R.id.rest, R.id.next)
                    viewModel.loadNotes(true)
                }
            }
        }
    }

    private val previousEnableObserver = Observer<Boolean> {
        binding.previousPost.setVisibility(it)
    }

    private val previousStepListener = View.OnClickListener {
        viewModel.loadNotes(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObservers()
        initEventListener()
    }

    private fun initObservers() = with(viewModel) {
        notes.observe(this@MainActivity, notesObserver)
        buttonEnabled.observe(this@MainActivity, previousEnableObserver)
    }

    private fun initEventListener() = with(binding) {
        previousPost.setOnClickListener(previousStepListener)
        motionLayout.setTransitionListener(transitionListener)
    }

    private fun handleSuccessLoadingNotes(stack: DeveloperStack) = with(binding) {
        when (stack.top) {
            is Result.Success -> doWhenResultIsSuccess(stack.top.data, authorName, imageHolder)
            is Result.Failure -> handleFailedLoadingNotes(stack.top.msg, authorName)
        }

        when (stack.bottom) {
            is Result.Success -> doWhenResultIsSuccess(
                stack.bottom.data,
                bottomAuthor,
                bottomHolder
            )
            is Result.Failure -> handleFailedLoadingNotes(stack.bottom.msg, bottomAuthor)
        }
    }

    private fun handleFailedLoadingNotes(msg: String?, view: TextView) {
        val defaultMsg = getString(R.string.default_msg_error)
        val message = msg ?: defaultMsg
        view.text = message
    }

    private fun doWhenResultIsSuccess(
        note: DeveloperNote?,
        authorView: TextView,
        imageView: ImageView
    ) {
        Glide.with(this@MainActivity)
            .asGif()
            .centerCrop()
            .placeholder(circularProgressDrawable)
            .load(note?.gifUrl)
            .into(imageView)
        authorView.text = note?.authorName
    }
}