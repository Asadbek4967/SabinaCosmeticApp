# Sabina Cosmetic Application

A modern Android cosmetic shopping application built with **Kotlin** and **Jetpack Compose**, designed with a clean architecture approach and focused on a smooth mobile shopping experience.

## Overview

Sabina Cosmetic Application is a beauty shopping app prototype that allows users to browse cosmetic products, view product details, search products, explore categories, and manage a shopping cart.

The project has been developed with a strong focus on:

- modern Android development practices
- clean and scalable architecture
- reusable UI components
- API integration
- local cart persistence
- professional project structure for future expansion

## Features Implemented

### Product Browsing
- Home screen with promotional banners
- Recommended product section
- Product category display
- Product cards with brand, title, category, price, and product image

### Product Detail
- Dedicated product detail screen
- Product image rendering
- Category badge and product status display
- Price section
- Product description and product information section
- Add to Cart action

### Search
- Search screen with query handling
- Product filtering based on keyword
- Search result display

### Categories
- Category browsing screen
- Category-based product filtering
- Category navigation flow

### Cart
- Add products to cart
- Increase and decrease item quantity
- Remove item from cart
- Undo removed item
- Cart subtotal and total price display
- Empty cart state
- Local cart persistence using Room database

## Architecture

This project follows a layered and scalable architecture:

- **feature** layer for presentation/UI
- **domain** layer for repository contracts and use cases
- **data** layer for remote, local, mapper, and repository implementations
- **di** layer for dependency injection modules
- **core/ui** shared reusable components and design system

### Main Technologies
- **Kotlin**
- **Jetpack Compose**
- **Hilt**
- **Retrofit**
- **Room**
- **Coil**
- **Coroutines**
- **StateFlow**
- **Navigation Compose**

## Project Structure

```text
app/
 ┣ data/
 ┃ ┣ local/
 ┃ ┣ remote/
 ┃ ┣ mapper/
 ┃ ┣ model/
 ┃ ┗ repository/
 ┣ domain/
 ┃ ┣ repository/
 ┃ ┗ usecase/
 ┣ feature/
 ┃ ┣ home/
 ┃ ┣ categories/
 ┃ ┣ categoryproducts/
 ┃ ┣ productdetail/
 ┃ ┣ search/
 ┃ ┣ cart/
 ┃ ┗ my/
 ┣ di/
 ┣ navigation/
 ┣ ui/
 ┃ ┗ components/
 ┗ core/

**Asadbek Matyaqubov**

Android Developer