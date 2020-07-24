package com.appspell.android.templates.mvvm.list

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.appspell.android.templates.R

const val DEBUG_LOG_TAG = "LiveDataInvestigation"

class MvvmListActivity : AppCompatActivity() {

    init {
        Log.i(DEBUG_LOG_TAG, "Activity.init")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(DEBUG_LOG_TAG, "Activity.onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvvm_list)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.root, MvvmListFragment())
                .commit()
        }
    }

    override fun onResume() {
        super.onResume()
        Log.i(DEBUG_LOG_TAG, "Activity.onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i(DEBUG_LOG_TAG, "Activity.onPause")
    }
}
