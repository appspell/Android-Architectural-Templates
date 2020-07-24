package com.appspell.android.templates.mvvm.list.di

import androidx.fragment.app.Fragment
import com.appspell.android.templates.mvvm.di.NetworkModule
import com.appspell.android.templates.mvvm.list.MvvmListFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        MvvmListModule::class,
        NetworkModule::class
    ]
)
interface MvvmListComponent {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun fragment(fragment: Fragment): Builder

        fun build(): MvvmListComponent
    }

    fun inject(fragment: MvvmListFragment)
}