package com.aidanvii.mybingelist.core.mappers

import com.aidanvii.mybingelist.core.AllMoviesByGenreQuery
import com.aidanvii.mybingelist.core.FavouritesQuery
import com.aidanvii.mybingelist.core.entities.Movie

fun AllMoviesByGenreQuery.Movie.toMovie() = Movie(
    id = id(),
    rating = rating(),
    posterImage = poster()?.fullPath(),
    isFavourite = false
)

fun FavouritesQuery.Favorite.toMovie() = Movie(
    id = id(),
    rating = rating(),
    posterImage = poster()?.fullPath(),
    isFavourite = true
)