package com.softaai.marvelcharacters.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.softaai.marvelcharacters.R
import com.softaai.marvelcharacters.ui.characters.viewmodel.CharacterListViewModel
import com.softaai.marvelcharacters.databinding.ActivityCharacterListBinding
import com.softaai.marvelcharacters.di.ViewModelFactory
import com.softaai.marvelcharacters.model.Model
import com.softaai.marvelcharacters.ui.characters.adapter.CharactersAdapter
import com.softaai.marvelcharacters.utils.HashUtils


/**
 * Created by Amol Pawar on 20-11-2018.
 * softAai Apps
 */

class CharacterListActivity: AppCompatActivity(), CharacterListViewModel.CharacterListActivityViewModel {

    private lateinit var binding: ActivityCharacterListBinding
    private lateinit var viewModel: CharacterListViewModel

    private var errorSnackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_character_list)
        binding.characterList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        viewModel = ViewModelProviders.of(this, ViewModelFactory(this)).get(CharacterListViewModel::class.java)
        viewModel.errorMessage.observe(this, Observer {
                errorMessage -> if(errorMessage != null) showError(errorMessage) else hideError()
        })
        binding.viewModel = viewModel

        viewModel.loadCharacters(this)

        binding.characterList.addOnScrollListener(HashUtils.InfiniteScrollListener({ viewModel.loadMoreCharacters(this,  binding.characterList.adapter as CharactersAdapter) },
            binding.characterList.layoutManager as LinearLayoutManager
        ))

    }

    private fun showError(@StringRes errorMessage:Int){
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
        errorSnackbar?.show()
    }
    private fun hideError(){
        errorSnackbar?.dismiss()
    }

    fun charactersList(characterResponse: Model.CharacterResponse) {
        val adapter = CharactersAdapter(characterResponse)
        binding.characterList.adapter = adapter
    }

    override fun endCallProgress(any: Any?) {
        if (any is Throwable) {
            println("Error: " + any.message)
        } else if (any is Model.CharacterResponse) {
            charactersList(any)
        }
    }

}