package com.syafi.favorite

import android.content.Context
import com.syafi.coolergituser.presentation.di.FavoriteModule
import com.syafi.favorite.presentation.ui.FavoriteFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [FavoriteModule::class]
)
interface FavoriteComponent {
    fun inject(favoriteFragment: FavoriteFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favoriteModuleDependencies: FavoriteModule): Builder
        fun build(): FavoriteComponent
    }
}