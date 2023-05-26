package com.hasanalic.cryptocurrencies.viewmodel

import androidx.lifecycle.ViewModel
import com.hasanalic.cryptocurrencies.model.Crypto
import com.hasanalic.cryptocurrencies.repository.CryptoRepository
import com.hasanalic.cryptocurrencies.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CryptoDetailViewModel @Inject constructor(
    private val repository: CryptoRepository
) : ViewModel() {

    suspend fun getCrypto() : Resource<Crypto> {
        return repository.getCrypto()
    }
}