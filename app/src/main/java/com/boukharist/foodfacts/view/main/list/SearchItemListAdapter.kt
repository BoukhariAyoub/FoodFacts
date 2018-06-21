package com.boukharist.foodfacts.view.main.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.boukharist.foodfacts.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class SearchItemListAdapter(var list: List<ProductSearchItem>,
                            private val onDetailSelected: (String) -> Unit
) : RecyclerView.Adapter<SearchItemListAdapter.ProductSearchHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductSearchHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product_search, parent, false)
        return ProductSearchHolder(view)
    }

    override fun onBindViewHolder(holder: ProductSearchHolder, position: Int) {
        holder.display(list[position], onDetailSelected)
    }

    override fun getItemCount() = list.size

    inner class ProductSearchHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val productItemLayout = item.findViewById<ViewGroup>(R.id.product_item_layout)
        private val productItemName = item.findViewById<TextView>(R.id.product_item_name)
        private val productItemImage = item.findViewById<ImageView>(R.id.product_item_image)

        fun display(item: ProductSearchItem, onClick: (String) -> Unit) {
            productItemLayout.setOnClickListener { onClick(item.id) }
            productItemName.text = item.name

            val myOptions = RequestOptions()
                    .centerCrop()
                    .override(100, 100)
            Glide.with(productItemImage.context)
                    .load(item.pictureUrl)
                    .apply(myOptions)
                    .into(productItemImage)

        }

    }
}