# 💄 Sabina Cosmetic Application

A modern Android e-commerce application built with **Jetpack Compose**, designed for browsing, searching, and purchasing cosmetic products with a clean and scalable architecture.

---

## 🚀 Features

### 🏠 Home & Discovery

* Product listing with categorized browsing
* Promotional banners and featured sections
* Best sellers and recommended products

### 🔍 Search

* Real-time product search
* Filtered results with responsive UI

### 📦 Product Details

* Detailed product information
* Add to cart functionality
* Favorite (wishlist) support

### ❤️ Favorites (Wishlist)

* Save and manage favorite products
* Quick add to cart
* Persistent local storage

### 🛒 Cart System

* Add/remove items
* Quantity management
* Dynamic total price calculation
* Undo actions via Snackbar

### 💳 Checkout Flow

* Delivery address input
* Payment method selection
* Order summary (subtotal, shipping, total)
* Place order functionality

### 📑 Orders

* Orders list with history
* Order detail view
* Multi-item order support
* Status tracking (Placed)

---

## 🧠 Architecture

The app follows a **Clean Architecture-inspired structure**:

```
data/
 ├── local/
 │   ├── dao/
 │   ├── entity/
 │   ├── relation/
 │   └── room/
 ├── mapper/
 └── repository/

domain/
 ├── repository/
 └── usecase/

feature/
 ├── home/
 ├── search/
 ├── productdetail/
 ├── favorites/
 ├── cart/
 └── orders/

ui/
 └── components/

di/
 └── dependency injection modules
```

---

## 🗄️ Local Database (Room)

* Cart persistence
* Favorites persistence
* Orders with relational structure:

    * `OrderEntity`
    * `OrderItemEntity`
    * `OrderWithItems`

Supports:

* Multi-item orders
* Transaction-safe queries
* Structured data mapping

---

## 🔄 Data Flow

```
UI → ViewModel → Repository → DAO (Room) → Database
```

State is managed using:

* `StateFlow`
* `UiState` pattern

---

## 🎨 UI & Design

* Built with **Jetpack Compose**
* Material 3 design principles
* Reusable UI components
* Responsive layouts
* Clean spacing and hierarchy

---

## ⚙️ Tech Stack

* Kotlin
* Jetpack Compose
* Room Database
* Hilt (Dependency Injection)
* Coroutines & Flow

---

## 📌 Current Status

The application has reached a **fully functional prototype stage**:

✅ Core e-commerce flow implemented
✅ Local persistence working
✅ Multi-screen navigation complete
✅ Order system (end-to-end) functional

---

## 🔜 Future Improvements

* UI/UX polishing (spacing, typography, design system)
* Currency localization (₩ support)
* Shipping logic (dynamic rules)
* Error handling & loading states
* Offline support
* Backend integration (API)
* Authentication system
* Payment integration

---

## 📷 Screens

* Home
* Product Detail
* Cart
* Checkout
* Orders
* Order Detail

---

## 👨‍💻 Author

**Asadbek Matyaqubov**

---

## 📄 License

This project is for educational and portfolio purposes.
