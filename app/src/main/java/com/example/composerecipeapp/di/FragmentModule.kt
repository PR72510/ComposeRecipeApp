package com.example.composerecipeapp.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.example.composerecipeapp.presentation.components.util.SnackbarController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.CoroutineScope

@Module
@InstallIn(FragmentComponent::class)
object FragmentModule {

    @Provides
    @FragmentScoped
    fun provideLifecycleCoroutineScope(fragment: Fragment): CoroutineScope =
        fragment.lifecycleScope

    @Provides
    @FragmentScoped
    fun provideSnackbarController(scope: CoroutineScope): SnackbarController{
        return SnackbarController(scope)
    }
}