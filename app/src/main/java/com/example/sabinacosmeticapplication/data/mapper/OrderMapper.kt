package com.example.sabinacosmeticapplication.data.mapper

import com.example.sabinacosmeticapplication.data.local.entity.OrderEntity
import com.example.sabinacosmeticapplication.data.local.entity.OrderItemEntity
import com.example.sabinacosmeticapplication.data.local.relation.OrderWithItems
import com.example.sabinacosmeticapplication.domain.model.Order
import com.example.sabinacosmeticapplication.domain.model.OrderItem
import com.example.sabinacosmeticapplication.feature.orders.OrderCardUi
import com.example.sabinacosmeticapplication.feature.orders.OrderItemUi

fun OrderWithItems.toDomain(): Order {
    return Order(
        orderId = order.id,
        createdAtMillis = order.createdAtMillis,
        status = order.status,
        items = items.map { item ->
            OrderItem(
                productId = item.productId,
                title = item.title,
                brand = item.brand,
                imageUrl = item.imageUrl,
                quantity = item.quantity,
                priceValue = item.price
            )
        },
        itemCount = order.itemCount,
        subtotalPrice = order.subtotalPrice,
        shippingFee = order.shippingPrice,
        totalPrice = order.totalPrice,
        deliveryName = "",
        deliveryPhone = "",
        deliveryAddress = "",
        paymentMethod = ""
    )
}

fun Order.toOrderEntity(): OrderEntity {
    return OrderEntity(
        id = orderId,
        status = status,
        createdAtMillis = createdAtMillis,
        itemCount = itemCount,
        subtotalPrice = subtotalPrice,
        shippingPrice = shippingFee,
        totalPrice = totalPrice
    )
}

fun Order.toOrderItemEntities(): List<OrderItemEntity> {
    return items.mapIndexed { index, item ->
        OrderItemEntity(
            id = "$orderId-$index",
            orderId = orderId,
            productId = item.productId,
            title = item.title,
            brand = item.brand,
            category = "",
            imageUrl = item.imageUrl,
            price = item.priceValue,
            quantity = item.quantity
        )
    }
}

fun Order.toOrderCardUi(): OrderCardUi {
    return OrderCardUi(
        orderId = orderId,
        itemCount = itemCount,
        subtotalPrice = subtotalPrice,
        shippingFee = shippingFee,
        totalPrice = totalPrice,
        status = status,
        createdAtMillis = createdAtMillis,
        items = items.map { item ->
            item.toUi()
        }
    )
}

fun OrderItem.toUi(): OrderItemUi {
    return OrderItemUi(
        productId = productId,
        title = title,
        brand = brand,
        imageUrl = imageUrl,
        quantity = quantity,
        priceValue = priceValue
    )
}