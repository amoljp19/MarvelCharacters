package com.softaai.marvelcharacters.ui.characters.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.util.Log
import android.view.View
import com.softaai.marvelcharacters.BuildConfig
import com.softaai.marvelcharacters.base.BaseViewModel
import com.softaai.marvelcharacters.model.Model
import com.softaai.marvelcharacters.network.MarvelApi
import com.softaai.marvelcharacters.ui.CharacterListActivity
import com.softaai.marvelcharacters.ui.characters.adapter.CharactersAdapter
import com.softaai.marvelcharacters.utils.HashUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject


/**
 * Created by Amol Pawar on 20-11-2018.
 * softAai Apps
 */

class CharacterListViewModel(val context: Context):BaseViewModel(){
    @Inject
    lateinit var marvelApi: MarvelApi

    private lateinit var subscription: Disposable



    val defaultLimit = 20
    var countLimit = 0

    lateinit var originalList: MutableList<Model.Character>

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    val errorMessage:MutableLiveData<Int> = MutableLiveData()

    interface CharacterListActivityViewModel { fun endCallProgress(any: Any?) }

    val errorClickListener = View.OnClickListener { }



     fun loadCharacters(callback: CharacterListActivityViewModel){
        val timestamp = Date().time;
        val hash = HashUtils.md5(timestamp.toString()+ BuildConfig.MARVEL_PRIVATE_KEY+BuildConfig.MARVEL_PUBLIC_KEY)

        subscription = marvelApi.getCharacters(timestamp.toString(), BuildConfig.MARVEL_PUBLIC_KEY, hash, defaultLimit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveCharacterListStart() }
            .doOnTerminate { onRetrieveCharacterListFinish() }
            .subscribe(
                { c -> callback.endCallProgress(c)
                    originalList = c.data.results
                    countLimit = c.data.limit
                },
                { e -> callback.endCallProgress(e)
                    Log.e(CharacterListActivity::class.java.simpleName, e.message) }
            )

    }

     fun loadMoreCharacters(callback: CharacterListActivityViewModel, adapter: CharactersAdapter){
        val timestamp = Date().time;
        val hash = HashUtils.md5(timestamp.toString()+ BuildConfig.MARVEL_PRIVATE_KEY+BuildConfig.MARVEL_PUBLIC_KEY)

        subscription = marvelApi.getCharacters(timestamp.toString(), BuildConfig.MARVEL_PUBLIC_KEY, hash, defaultLimit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveCharacterListStart() }
            .doOnTerminate { onRetrieveCharacterListFinish() }
            .subscribe(
                {  c -> callback.endCallProgress(c)
                    updateIndexesForRequests(adapter, c)},
                { e -> callback.endCallProgress(e)
                    Log.e(CharacterListActivity::class.java.simpleName, e.message) }
            )
    }

    private fun onRetrieveCharacterListStart(){
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrieveCharacterListFinish(){

        loadingVisibility.value = View.GONE
    }

    fun updateIndexesForRequests(adapter: CharactersAdapter, response: Model.CharacterResponse) {
        adapter.characterResponse = response
        adapter.notifyItemRangeChanged(countLimit, countLimit + defaultLimit)
        originalList = response.data.results
        countLimit += defaultLimit
    }


    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }
}
