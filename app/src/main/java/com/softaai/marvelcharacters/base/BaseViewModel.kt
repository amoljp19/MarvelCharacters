package com.softaai.marvelcharacters.base

import android.arch.lifecycle.ViewModel
import com.softaai.marvelcharacters.di.module.NetworkModule
import com.softaai.marvelcharacters.di.component.DaggerViewModelInjector
import com.softaai.marvelcharacters.di.component.ViewModelInjector
import com.softaai.marvelcharacters.ui.characters.viewmodel.CharacterListViewModel


/**
 * Created by Amol Pawar on 20-11-2018.
 * softAai Apps
 */

abstract  class BaseViewModel:ViewModel(){
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is CharacterListViewModel -> injector.inject(this)
        }
    }
}