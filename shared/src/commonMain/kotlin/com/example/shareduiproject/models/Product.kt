package com.example.shareduiproject.models

data class Product(
    val category: Category? = null,
    val description: String? = null,
    val id: Int? = null,
    val images: List<String>? = null,
    val price: Int? = null,
    val slug: String? = null,
    val title: String? = null
)