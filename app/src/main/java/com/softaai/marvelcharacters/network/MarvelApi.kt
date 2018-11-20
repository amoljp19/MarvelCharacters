package com.softaai.marvelcharacters.network

import com.softaai.marvelcharacters.model.Model
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.*


/**
 * Created by Amol Pawar on 20-11-2018.
 * softAai Apps
 */

interface MarvelApi {

    @Headers("Accept: */*")
    @GET("/v1/public/characters")
    public fun getCharacters(
        @Query("ts") ts: String,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String,
        @Query("limit") limit: Int
    ) : Observable<Model.CharacterResponse>

}