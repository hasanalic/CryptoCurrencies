package com.hasanalic.cryptocurrencies.repository

import com.hasanalic.cryptocurrencies.model.Crypto
import com.hasanalic.cryptocurrencies.model.CryptoList
import com.hasanalic.cryptocurrencies.util.Resource

interface CryptoRepository {

    suspend fun getCryptoList(): Resource<CryptoList>

    suspend fun getCrypto(): Resource<Crypto>
}