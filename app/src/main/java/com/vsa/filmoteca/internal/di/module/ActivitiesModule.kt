package com.vsa.filmoteca.internal.di.module

import com.vsa.filmoteca.internal.di.scope.PerActivity
import com.vsa.filmoteca.presentation.presenter.detail.di.DetailModule
import com.vsa.filmoteca.presentation.presenter.movieslist.di.MoviesListModule
import com.vsa.filmoteca.presentation.view.activity.DetailActivity
import com.vsa.filmoteca.presentation.view.activity.MoviesListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector(modules = [MoviesListModule::class])
    @PerActivity
    abstract fun provideMoviesListActivity(): MoviesListActivity

    @ContributesAndroidInjector(modules = [DetailModule::class])
    @PerActivity
    abstract fun provideDetailActivity(): DetailActivity

}