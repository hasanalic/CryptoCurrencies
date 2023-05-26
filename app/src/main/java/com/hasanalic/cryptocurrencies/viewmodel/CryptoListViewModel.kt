package com.hasanalic.cryptocurrencies.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hasanalic.cryptocurrencies.model.CryptoListItem
import com.hasanalic.cryptocurrencies.repository.CryptoRepository
import com.hasanalic.cryptocurrencies.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoListViewModel @Inject constructor(
    private val repository: CryptoRepository
) : ViewModel() {

    // bu mutableState tipindeki cryptoList, "listOf()" ile ilk başta boş bir listeye sahip oluyor.
    // fakat list'in tipini belirtmemiz gerekir ->  List<CryptoListItem>
    var cryptoList = mutableStateOf<List<CryptoListItem>>(listOf())
    var errorMessage = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    private var initialCryptoList = listOf<CryptoListItem>()
    private var isSearchStarting = true

    // viewmodel çağırıldığı an loadCryptos çağırılacak
    init {
        loadCryptos()
    }

    fun searchCryptoList(query: String) {
        val listToSearch = if (isSearchStarting) {
            cryptoList.value
        } else {
            initialCryptoList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                cryptoList.value = initialCryptoList
                isSearchStarting = true
                return@launch
            }
            val results = listToSearch.filter {
                // currency değerine "contains" ile tarama yaptırırsak
                // içinde query değeri geçen tüm currency'ler alınmış olur.
                it.currency.contains(query.trim(), ignoreCase = true)
            }

            if (isSearchStarting) {
                initialCryptoList = cryptoList.value
                isSearchStarting = false
            }

            cryptoList.value = results
        }
    }

    fun loadCryptos() {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getCryptoList()
            when(result) {
                is Resource.Success -> {
                    // result.data -> ArrayList<CryptoListItem> döndürüyor.
                    // map kullanarak bu arraylist içinde geziniyoruz.
                    // her bir item(CryptoListItem sınıfından)i alıp yeni bir CryptoListItem objesi oluşturuyoruz.
                    // bu objeler de cryptoItems LIST'ine ekleniyor tek tek.
                    val cryptoItems = result.data!!.mapIndexed { index, cryptoListItem ->
                        CryptoListItem(cryptoListItem.currency,cryptoListItem.price)
                    } as List<CryptoListItem>

                    cryptoList.value += cryptoItems

                    errorMessage.value = ""
                    isLoading.value = false
                }
                is Resource.Error -> {
                    errorMessage.value = result.message ?: "Error in viewmodel"
                    isLoading.value = false
                }
                is Resource.Loading -> {}
            }
        }
    }
}