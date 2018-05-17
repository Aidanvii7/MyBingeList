package com.aidanvii.mybingelist.search.usecases

import com.aidanvii.mybingelist.core.AllMoviesByGenreQuery
import com.aidanvii.mybingelist.core.entities.Movie
import com.aidanvii.mybingelist.core.mappers.toMovie
import com.aidanvii.toolbox.rxutils.RxSchedulers
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.rx2.Rx2Apollo
import io.reactivex.Single

class LoadSearch(
    private val apolloClient: ApolloClient,
    private val rxSchedulers: RxSchedulers
) : (String) -> Single<Set<Movie>> {

    override fun invoke(searchQuery: String): Single<Set<Movie>> =
        if (searchQuery.isBlank()) {
            Single.just(emptySet())
        } else {
            Single.fromObservable(
                Rx2Apollo.from(
                    apolloClient.query(
                        AllMoviesByGenreQuery.builder()
                            .genre(searchQuery.substringBefore(delimiter = ' '))
                            .build()
                    )
                ).subscribeOn(rxSchedulers.io).map {
                    it.data()?.allMovies()?.movies()?.map { it.toMovie() }?.toSet() ?: emptySet()
                }
            )
        }

}

