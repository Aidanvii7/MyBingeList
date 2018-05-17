package com.aidanvii.mybingelist

import com.aidanvii.mybingelist.core.entities.Movie

fun movie(
    id: Long,
    isFavourite: Boolean = false
) = Movie(
    id = id,
    rating = 1.0,
    isFavourite = isFavourite,
    posterImage = null
)