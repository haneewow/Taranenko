package com.example.sirius.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.sirius.R
import com.example.sirius.databinding.FragmentInfoBinding
import com.example.sirius.domain.model.data.DeveloperNote
import com.example.sirius.ui.MainFragment.Companion.BUNDLE_NOTE_KEY
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class InfoBottomSheetFragment : BottomSheetDialogFragment() {
    private var binding: FragmentInfoBinding? = null

    private val note: DeveloperNote
        get() = arguments?.getSerializable(BUNDLE_NOTE_KEY) as DeveloperNote

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun setUpViews() {
        binding?.authorName?.text = note.authorName
        binding?.date?.text = note.date
        binding?.likeCount?.text = note.votes.toString()
        binding?.likeImg?.setImageDrawable(getVoteDrawable())
    }

    private fun getVoteDrawable(): Drawable? {
        val countVotes = note.votes ?: DEFAULT_VOTES
        val resId = if (countVotes >= DEFAULT_VOTES) R.drawable.ic_like
        else R.drawable.ic_dislike

        return ContextCompat.getDrawable(requireActivity(), resId)
    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle) = InfoBottomSheetFragment().apply {
            arguments = bundle
        }

        private const val DEFAULT_VOTES = 0
    }
}