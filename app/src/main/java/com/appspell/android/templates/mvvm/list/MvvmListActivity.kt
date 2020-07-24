package com.appspell.android.templates.mvvm.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.appspell.android.templates.R

class MvvmListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvvm_list)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.root, MvvmListFragment())
                .commit()
        }
    }
}
