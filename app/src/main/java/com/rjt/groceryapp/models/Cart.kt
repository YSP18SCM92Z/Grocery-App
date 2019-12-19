package com.rjt.groceryapp.models

data class Cart (
    var cartId: String,
    var productName: String,
    var Image: String,
    var price: Double,
    var qty: Int
)