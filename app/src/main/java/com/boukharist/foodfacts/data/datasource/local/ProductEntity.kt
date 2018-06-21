package com.boukharist.foodfacts.data.datasource.local

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.boukharist.foodfacts.data.datasource.remote.ProductResponse

@Entity(tableName = "product")
data class ProductEntity(
        @PrimaryKey
        val id: String,
        val name: String,
        val picture: String?,
        val ingredients: String?,
        val energy: String?) {

    companion object {
        fun from(response: ProductResponse) = ProductEntity(
                response.id ?: "",
                response.name ?: "",
                response.pictureUrl,
                response.ingredients,
                response.nutriments?.energy)
    }
}