package com.hasanalic.cryptocurrencies.service

import com.hasanalic.cryptocurrencies.model.Crypto
import com.hasanalic.cryptocurrencies.model.CryptoList
import retrofit2.Response
import retrofit2.http.GET

interface CryptoAPI {

    @GET("cryptolist.json")
    suspend fun getList(): Response<CryptoList>

    @GET("crypto.json")
    suspend fun getCrypto() : Response<Crypto>
}