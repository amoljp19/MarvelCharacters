package com.softaai.marvelcharacters.ui.characters.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.softaai.marvelcharacters.R
import com.softaai.marvelcharacters.databinding.ItemCharacterBinding
import com.softaai.marvelcharacters.model.Model
import com.softaai.marvelcharacters.ui.characters.viewmodel.CharacterViewModel


/**
 * Created by Amol Pawar on 20-11-2018.
 * softAai Apps
*/

class CharactersAdapter(var characterResponse: Model.CharacterResponse) : RecyclerView.Adapter<CharactersAdapter.ItemCharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCharacterViewHolder {
        val itemCharacterBinding = DataBindingUtil.inflate<ItemCharacterBinding>(LayoutInflater.from(parent.context), R.layout.item_character, parent, false)

        return ItemCharacterViewHolder(
            itemCharacterBinding
        )

    }

    override fun onBindViewHolder(holder: ItemCharacterViewHolder, position: Int) {
        holder.bindItemCharacter(characterResponse.data.results[position])
    }

    override fun getItemCount() = characterResponse.data.results.size

    class ItemCharacterViewHolder(val binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.cardView) {
        fun bindItemCharacter(character: Model.Character) {
            var viewmodel = CharacterViewModel(itemView.context, character)
            binding.viewmodel = viewmodel

        }

    }

}
