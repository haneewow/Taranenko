package com.example.sirius

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sirius.ui.InfoBottomSheetFragment
import com.example.sirius.ui.MainFragment

class MainActivity : AppCompatActivity(), MainFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun sendNote(bundle: Bundle) {
        InfoBottomSheetFragment.newInstance(bundle).apply {
            show(supportFragmentManager, null)
        }
    }
}