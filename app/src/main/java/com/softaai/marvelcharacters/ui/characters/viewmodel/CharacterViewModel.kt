package com.softaai.marvelcharacters.ui.characters.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.softaai.marvelcharacters.base.BaseViewModel
import com.softaai.marvelcharacters.model.Model


/**
 * Created by Amol Pawar on 20-11-2018.
 * softAai Apps
 */

class CharacterViewModel(val context: Context, var model: Model.Character): BaseViewModel(){

    companion object {
        val IMAGE_TYPE = "/landscape_incredible."
    }

    var imageUrl = modelImageUrl()

    fun modelImageUrl(): String = model.thumbnail.path + IMAGE_TYPE + model.thumbnail.extension

    object ImageViewBindingAdapter {
        @BindingAdapter("bind:imageUrl")
        @JvmStatic
        fun loadImage(view: ImageView, url: String) {
            Glide.with(view.context).load(url).into(view)
        }
    }

}
