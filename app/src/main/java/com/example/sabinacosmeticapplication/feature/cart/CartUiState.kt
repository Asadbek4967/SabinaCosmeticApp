package com.example.sabinacosmeticapplication.feature.cart

data class CartUiState(
    val isLoading: Boolean = true,
    val items: List<CartItemUi> = emptyList(),
    val subtotalPrice: Int = 0,
    val shippingPrice: Int = 0,
    val totalPrice: Int = 0,
    val errorMessage: String? = null,
    val emptyState: CartEmptyState = CartEmptyState(),
    val isClearCartDialogVisible: Boolean = false
) {

    val totalItems: Int
        get() = items.sumOf(CartItemUi::quantity)

    val isEmpty: Boolean
        get() = items.isEmpty()

    val hasItems: Boolean
        get() = items.isNotEmpty()

    val showContent: Boolean
        get() = !isLoading && hasItems && errorMessage == null

    val showEmptyState: Boolean
        get() = !isLoading && isEmpty && errorMessage == null

    val showErrorState: Boolean
        get() = !isLoading && errorMessage != null

    companion object {
        const val FREE_SHIPPING_THRESHOLD = 50_000
        const val DEFAULT_SHIPPING_PRICE = 3_000
    }
}