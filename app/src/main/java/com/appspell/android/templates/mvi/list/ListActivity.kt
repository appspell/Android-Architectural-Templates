package com.appspell.android.templates.mvi.list

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.appspell.android.templates.R

class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvi_list)
    }
}
