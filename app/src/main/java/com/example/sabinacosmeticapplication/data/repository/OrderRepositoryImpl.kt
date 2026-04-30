package com.example.sabinacosmeticapplication.data.repository

import com.example.sabinacosmeticapplication.data.local.dao.OrderDao
import com.example.sabinacosmeticapplication.data.local.entity.OrderEntity
import com.example.sabinacosmeticapplication.data.local.entity.OrderItemEntity
import com.example.sabinacosmeticapplication.data.local.relation.OrderWithItems
import com.example.sabinacosmeticapplication.domain.repository.OrderLineRequest
import com.example.sabinacosmeticapplication.domain.repository.OrderRepository
import java.util.UUID
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class OrderRepositoryImpl @Inject constructor(
    private val orderDao: OrderDao
) : OrderRepository {

    override suspend fun placeOrder(
        items: List<OrderLineRequest>,
        subtotalPrice: Int,
        shippingPrice: Int,
        totalPrice: Int
    ) {
        val orderId = UUID.randomUUID().toString().take(8)
        val createdAt = System.currentTimeMillis()

        val order = OrderEntity(
            id = orderId,
            status = "Placed",
            createdAtMillis = createdAt,
            itemCount = items.sumOf { it.quantity },
            subtotalPrice = subtotalPrice,
            shippingPrice = shippingPrice,
            totalPrice = totalPrice
        )

        val orderItems = items.mapIndexed { index, item ->
            OrderItemEntity(
                id = "$orderId-$index",
                orderId = orderId,
                productId = item.productId,
                title = item.title,
                brand = item.brand,
                category = item.category,
                imageUrl = item.imageUrl,
                price = item.price,
                quantity = item.quantity
            )
        }

        orderDao.insertOrder(order)
        orderDao.insertOrderItems(orderItems)
    }

    override fun observeOrders(): Flow<List<OrderWithItems>> = orderDao.observeOrders()

    override suspend fun getOrderDetail(orderId: String): OrderWithItems {
        return orderDao.getOrderWithItems(orderId)
    }
}