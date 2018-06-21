package com.boukharist.foodfacts.data.repository

import com.boukharist.foodfacts.data.datasource.local.ProductDao
import com.boukharist.foodfacts.data.datasource.local.ProductEntity
import com.boukharist.foodfacts.data.datasource.remote.ProductRemoteDataSource
import com.boukharist.foodfacts.util.ProductNotFoundException
import io.reactivex.Single

interface ProductRepository {
    fun insertProduct(productEntity: ProductEntity)
    fun getProductById(id: String): Single<ProductEntity>
    fun getAllProducts(): Single<List<ProductEntity>>
}

class ProductRepositoryImpl(private val remoteDataSource: ProductRemoteDataSource,
                            private val localDataSource: ProductDao) : ProductRepository {

    override fun insertProduct(productEntity: ProductEntity) {
        return localDataSource.save(productEntity)
    }

    override fun getProductById(id: String): Single<ProductEntity> {
        return localDataSource.findById(id)
                .switchIfEmpty(getAndSaveProductFromRemote(id))
    }


    override fun getAllProducts(): Single<List<ProductEntity>> {
        return localDataSource.findAll()
    }


    private fun getAndSaveProductFromRemote(id: String): Single<ProductEntity>? {
        return remoteDataSource.getProductById(id)
                .flatMap {
                    if (it.product != null) {
                        Single.just(ProductEntity.from(it.product))
                    } else {
                        Single.error<ProductEntity>(ProductNotFoundException(it.statusVerbose))
                    }
                }
                .doOnSuccess {
                    insertProduct(it)
                }
    }
}