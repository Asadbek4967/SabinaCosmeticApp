package com.example.sabinacosmeticapplication.feature.home

import androidx.lifecycle.ViewModel
import com.example.sabinacosmeticapplication.data.repository.FakeProductRepository
import com.example.sabinacosmeticapplication.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {

    private val repository: ProductRepository = FakeProductRepository()

    private val allProducts = repository.getAllProducts()

    private val _uiState = MutableStateFlow(
        HomeUiState(
            flashSaleProducts = allProducts.filter { it.isFlashSale },
            bestSellerProducts = allProducts.filter { it.isBestSeller },
            recommendedProducts = allProducts
        )
    )

    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
}