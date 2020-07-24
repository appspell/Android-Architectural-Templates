package com.appspell.android.templates.mvvm.list.di

import androidx.appcompat.app.AppCompatActivity
import com.appspell.android.templates.mvvm.di.NetworkModule
import com.appspell.android.templates.mvvm.list.MvvmListActivity
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
        fun activity(activity: AppCompatActivity): Builder

        fun build(): MvvmListComponent
    }

    fun inject(activity: MvvmListActivity)
}