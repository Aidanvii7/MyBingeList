package com.aidanvii.mybingelist.core.entities

data class Movie(
    val id: Long,
    val rating: Double,
    val isFavourite: Boolean,
    val posterImage: String?
)