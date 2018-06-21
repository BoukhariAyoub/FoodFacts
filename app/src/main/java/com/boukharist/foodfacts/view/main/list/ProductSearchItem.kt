package com.boukharist.foodfacts.view.main.list

import com.boukharist.foodfacts.data.datasource.local.ProductEntity

data class ProductSearchItem(val id: String, val name: String, val pictureUrl: String?) {
    companion object {
        fun from(productEntity: ProductEntity) = ProductSearchItem(
                productEntity.id,
                productEntity.name,
                productEntity.picture)
    }
}