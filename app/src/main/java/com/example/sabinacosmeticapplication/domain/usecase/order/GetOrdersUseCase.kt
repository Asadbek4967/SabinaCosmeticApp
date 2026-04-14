package com.example.sabinacosmeticapplication.domain.usecase.order

import com.example.sabinacosmeticapplication.domain.repository.OrderRepository
import javax.inject.Inject

class GetOrdersUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    operator fun invoke() = orderRepository.observeOrders()
}