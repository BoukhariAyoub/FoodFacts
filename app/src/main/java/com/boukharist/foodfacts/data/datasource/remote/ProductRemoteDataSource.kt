package com.boukharist.foodfacts.data.datasource.remote

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductRemoteDataSource {

    @GET("/api/v0/product/{id}.json")
    fun getProductById(@Path("id") id: String): Single<ProductSearchResponse>
}