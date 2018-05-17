package com.aidanvii.mybingelist.genres.usecases

import com.aidanvii.mybingelist.core.GenresQuery
import com.aidanvii.toolbox.rxutils.RxSchedulers
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.rx2.Rx2Apollo
import io.reactivex.Single

class LoadGenres(
    private val apolloClient: ApolloClient,
    private val rxSchedulers: RxSchedulers
) : () -> Single<Set<String>> {

    override fun invoke(): Single<Set<String>> =
        Single.fromObservable(
            Rx2Apollo.from(
                apolloClient.query(GenresQuery.builder().build())
            ).subscribeOn(rxSchedulers.io).map { it.data()?.genres()?.toSet() }

        )
}