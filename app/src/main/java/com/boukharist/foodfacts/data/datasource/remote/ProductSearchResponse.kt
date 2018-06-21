package com.boukharist.foodfacts.data.datasource.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ProductSearchResponse(
        @SerializedName("code") @Expose val code: String? = null,
        @SerializedName("status")
        @Expose
        val status: Int? = null,
        @SerializedName("status_verbose")
        @Expose
        val statusVerbose: String? = null,
        @SerializedName("product")
        @Expose
        val product: ProductResponse? = null)

data class ProductResponse(
        @SerializedName("id") @Expose var id: String? = null,
        @SerializedName("ingredients_text_fr")
        @Expose
        var ingredients: String? = null,
        @SerializedName("image_front_url")
        @Expose
        var pictureUrl: String? = null,
        @SerializedName("product_name")
        @Expose
        var name: String? = null,
        @Expose
        @SerializedName("nutriments")
        var nutriments: Nutriments? = null)

data class Nutriments(
        @SerializedName("energy_value")
        @Expose var energy: String? = null)


