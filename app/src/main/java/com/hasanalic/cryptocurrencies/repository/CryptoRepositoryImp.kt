package com.hasanalic.cryptocurrencies.repository

import com.hasanalic.cryptocurrencies.model.Crypto
import com.hasanalic.cryptocurrencies.model.CryptoList
import com.hasanalic.cryptocurrencies.service.CryptoAPI
import com.hasanalic.cryptocurrencies.util.Resource
import javax.inject.Inject

class CryptoRepositoryImp @Inject constructor(
    private val retrofit: CryptoAPI
) : CryptoRepository{

    override suspend fun getCryptoList(): Resource<CryptoList> {
        return try {
            val response = retrofit.getList()
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.Success(it)
                } ?: Resource.Error("Failed", null)
            } else {
                return Resource.Error("Failed", null)
            }
        } catch (e: Exception) {
            return Resource.Error(e.localizedMessage ?: "Error", null)
        }
    }

    override suspend fun getCrypto(): Resource<Crypto> {
        return try {
            val response = retrofit.getCrypto()
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.Success(it)
                } ?: Resource.Error("Failed", null)
            } else {
                return Resource.Error("Failed",null)
            }
        } catch (e: Exception) {
            return Resource.Error(e.localizedMessage ?: "Error",null)
        }
    }

}