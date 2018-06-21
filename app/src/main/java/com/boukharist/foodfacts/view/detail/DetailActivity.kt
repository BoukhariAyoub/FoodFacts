package com.boukharist.foodfacts.view.detail

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.View.VISIBLE
import com.boukharist.foodfacts.R
import com.boukharist.foodfacts.util.NoNetworkException
import com.boukharist.foodfacts.util.ProductNotFoundException
import com.boukharist.foodfacts.util.extra
import com.boukharist.foodfacts.util.getTag
import com.boukharist.foodfacts.view.ErrorState
import com.boukharist.foodfacts.view.LoadingState
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.detail_view_layout.*
import kotlinx.android.synthetic.main.state_view_layout.*
import org.koin.android.architecture.ext.viewModel
import android.view.animation.Animation
import android.R.attr.duration
import android.view.View.GONE
import android.view.animation.AlphaAnimation
import java.time.Duration


class DetailActivity : AppCompatActivity() {

    private val viewModel by viewModel<DetailViewModel>()
    private val productId by extra<String>(EXTRA_PRODUCT_ID)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setupToolbar()

        //observe viewModel
        viewModel.states.observe(this, Observer { state ->
            when (state) {
                is ErrorState -> showError(state.error)
                LoadingState -> showLoading()
                is DetailViewModel.LoadedState -> showDetail(state.value)
            }
        })

        //load data
        viewModel.getProduct(productId)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        collapsing_toolbar_layout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.white))
        collapsing_toolbar_layout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.white))
        app_bar_layout.setExpanded(false)
    }

    private fun showDetail(item: DetailViewModel.DisplayableItem) {
        Log.i(getTag(), "$item")

        state_layout.visibility = GONE
        state_content_text_view.clearAnimation()

        detail_view_layout.visibility = VISIBLE
        app_bar_layout.setExpanded(true, true)
        Glide.with(this)
                .load(item.pictureUrl)
                .apply(RequestOptions().centerCrop())
                .into(expandedImage)

        collapsing_toolbar_layout.title = item.name

        item.ingredients?.let {
            ingredients_title_text_view.visibility = VISIBLE
            ingredients_text_view.visibility = VISIBLE
            ingredients_text_view.text = it
        }

        item.energy?.let {
            separator.visibility = VISIBLE
            energy_title_text_view.visibility = VISIBLE
            energy_text_view.visibility = VISIBLE
            energy_text_view.text = getString(R.string.energy_placeholder, it)
        }
    }

    private fun showLoading() {
        //show state layout
        state_layout.visibility = View.VISIBLE
        state_content_text_view.setText(R.string.loading_text)
        state_image_view.setImageResource(R.drawable.ic_loading)

        //blink animation
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 500
        anim.startOffset = 100
        anim.repeatMode = Animation.REVERSE
        anim.repeatCount = Animation.INFINITE
        state_content_text_view.startAnimation(anim)
    }

    private fun showError(error: Throwable) {
        @StringRes val message: Int
        @DrawableRes val image: Int

        when (error) {
            is ProductNotFoundException -> {
                image = R.drawable.ic_not_found_error
                message = R.string.product_not_found_message
            }
            is NoNetworkException -> {
                image = R.drawable.ic_network_off
                message = R.string.network_exception_message
            }
            else -> {
                image = R.drawable.ic_error
                message = R.string.unknown_error
            }
        }

        state_layout.visibility = View.VISIBLE
        state_content_text_view.setText(message)
        state_image_view.setImageResource(image)
        state_content_text_view.clearAnimation()
    }

    companion object {
        private const val EXTRA_PRODUCT_ID = "EXTRA_PRODUCT_ID"
        fun newIntent(context: Context, productId: String): Intent {
            return Intent(context, DetailActivity::class.java).apply {
                putExtra(EXTRA_PRODUCT_ID, productId)
            }
        }
    }
}
