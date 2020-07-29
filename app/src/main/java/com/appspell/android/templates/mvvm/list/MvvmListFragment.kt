package com.appspell.android.templates.mvvm.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.appspell.android.templates.R
import com.appspell.android.templates.mvvm.list.di.DaggerMvvmListComponent
import javax.inject.Inject

class MvvmListFragment : Fragment() {

    @Inject
    lateinit var binder: MvvmListBinder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerMvvmListComponent.builder()
            .fragment(this)
            .build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mvvm_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binder.bindView(view)
        binder.bindLifecycle(viewLifecycleOwner)
    }
}