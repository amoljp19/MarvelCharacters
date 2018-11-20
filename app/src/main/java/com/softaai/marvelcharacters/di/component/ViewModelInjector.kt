package com.softaai.marvelcharacters.di.component

import com.softaai.marvelcharacters.di.module.NetworkModule
import com.softaai.marvelcharacters.ui.characters.viewmodel.CharacterListViewModel
import dagger.Component
import javax.inject.Singleton


/**
 * Created by Amol Pawar on 20-11-2018.
 * softAai Apps
 */

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {

    fun inject(characterListViewModel: CharacterListViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}