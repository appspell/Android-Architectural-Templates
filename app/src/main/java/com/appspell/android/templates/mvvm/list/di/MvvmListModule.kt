package com.appspell.android.templates.mvvm.list.di

import androidx.fragment.app.Fragment
import com.appspell.android.templates.mvvm.base.viewModel
import com.appspell.android.templates.mvvm.list.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
abstract class MvvmListModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideViewModel(
            fragment: Fragment,
            repository: MvvmListViewRepository
        ): MvvmListViewModel =
            fragment.viewModel { MvvmListViewModelImpl(repository = repository) }

        @Provides
        @JvmStatic
        fun provideGithubAPI(retrofit: Retrofit): ApiService =
            retrofit.create(ApiService::class.java)
    }

    @Binds
    abstract fun bindBinder(impl: MvvmListBinderImpl): MvvmListBinder

    @Binds
    abstract fun bindRepository(impl: MvvmListViewRepositoryImpl): MvvmListViewRepository

    @Binds
    abstract fun bindRouter(impl: MvvmListRouterImpl): MvvmListRouter

    @Binds
    abstract fun bindView(impl: MvvmListViewImpl): MvvmListView
}