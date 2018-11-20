package com.softaai.marvelcharacters.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.v7.app.AppCompatActivity
import com.softaai.marvelcharacters.ui.characters.viewmodel.CharacterListViewModel


/**
 * Created by Amol Pawar on 21-11-2018.
 * softAai Apps
 */

class ViewModelFactory(private val activity: AppCompatActivity): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharacterListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CharacterListViewModel(activity) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}