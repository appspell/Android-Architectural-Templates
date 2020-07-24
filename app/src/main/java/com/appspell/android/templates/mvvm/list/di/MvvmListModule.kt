package com.appspell.android.templates.mvvm.list.di

import androidx.appcompat.app.AppCompatActivity
import com.appspell.android.templates.mvvm.base.viewModel
import com.appspell.android.templates.mvvm.list.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
abstract class MvvmListModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideViewModel(
            activity: AppCompatActivity,
            repository: MvvmListViewRepository
        ): MvvmListViewModel =
            activity.viewModel { MvvmListViewModelImpl(repository = repository) }

        @Provides
        @JvmStatic
        fun provideGithubAPI(retrofit: Retrofit): ApiService =
            retrofit.create(ApiService::class.java)
    }

    @Binds
    abstract fun bindBinder(impl: MvvmListBinderImpl): MvvmListBinder

    @Binds
    @Singleton
    abstract fun bindRepository(impl: MvvmListViewRepositoryImpl): MvvmListViewRepository

    @Binds
    abstract fun bindRouter(impl: MvvmListRouterImpl): MvvmListRouter

    @Binds
    abstract fun bindView(impl: MvvmListViewImpl): MvvmListView
}