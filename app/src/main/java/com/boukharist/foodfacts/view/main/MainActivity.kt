package com.boukharist.foodfacts.view.main

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.boukharist.foodfacts.R
import com.boukharist.foodfacts.util.getTag
import com.boukharist.foodfacts.view.detail.DetailActivity
import com.boukharist.foodfacts.view.main.list.SearchItemListAdapter
import kotlinx.android.synthetic.main.activity_main_content.*
import org.koin.android.architecture.ext.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainViewModel>()

    override fun onResume() {
        super.onResume()
        //load data
        viewModel.getAllProducts()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecycler()

        imageButton.setOnClickListener {
            //clear error
            search_input_layout.error = null
            search_input_layout.isErrorEnabled = false

            val code = search_input_layout.editText?.text?.toString()
            if (code != null && code.isNotEmpty()) {
                val intent = DetailActivity.newIntent(this, code)
                startActivity(intent)
            } else {
                //set error
                search_input_layout.error = getString(R.string.invalid_search_input)
            }
        }

        //observe viewModel
        viewModel.products.observe(this, Observer { products ->
            products?.let {
                Log.d(getTag(), "${products.size}")
                cardview.visibility = View.VISIBLE
                val adapter: SearchItemListAdapter = recycler_view.adapter as SearchItemListAdapter
                adapter.list = products
                adapter.notifyDataSetChanged()
            }
        })
    }

    private fun setupRecycler() {
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = SearchItemListAdapter(emptyList()) { productId -> onItemSelected(productId) }
    }

    private fun onItemSelected(productId: String) {
        startActivity(DetailActivity.newIntent(this, productId))
    }
}
