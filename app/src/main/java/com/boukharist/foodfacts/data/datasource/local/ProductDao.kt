package com.boukharist.foodfacts.data.datasource.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(product: ProductEntity)

    @Query("SELECT * FROM product")
    fun findAll(): Single<List<ProductEntity>>

    @Query("SELECT * FROM product WHERE id=:id")
    fun findById(id: String): Maybe<ProductEntity>
}