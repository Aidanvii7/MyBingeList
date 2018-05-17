package com.aidanvii.mybingelist.di

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.CacheKeyResolver
import com.apollographql.apollo.cache.normalized.sql.ApolloSqlHelper
import com.apollographql.apollo.cache.normalized.sql.SqlNormalizedCacheFactory
import okhttp3.OkHttpClient

class NetworkComponent(val appComponent: AppComponent) {

    val baseUrl = "http://192.168.1.38:3030/graphql"

    val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder().build()
    }

    val apolloClient: ApolloClient by lazy {
        ApolloClient.builder()
            .serverUrl(baseUrl)
            .normalizedCache(
                SqlNormalizedCacheFactory(ApolloSqlHelper.create(appComponent.applicationContext)),
                CacheKeyResolver.DEFAULT
            )
            .okHttpClient(okHttpClient)
            .build();
    }
}