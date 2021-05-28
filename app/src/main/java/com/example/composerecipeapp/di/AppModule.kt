package com.example.composerecipeapp.di

import android.content.Context
import com.example.composerecipeapp.BaseApplication
import com.example.composerecipeapp.network.RecipeService
import com.example.composerecipeapp.network.model.RecipeDtoMapper
import com.example.composerecipeapp.repository.RecipeRepository
import com.example.composerecipeapp.repository.RecipeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }

    @Singleton
    @Provides
    fun provideRepository(service: RecipeService, mapper: RecipeDtoMapper): RecipeRepository {
        return RecipeRepositoryImpl(service, mapper)
    }
}