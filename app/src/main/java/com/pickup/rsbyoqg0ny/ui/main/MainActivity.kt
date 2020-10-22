package com.pickup.rsbyoqg0ny.ui.main

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.pickup.rsbyoqg0ny.R
import com.pickup.rsbyoqg0ny.data.model.Movie
import com.pickup.rsbyoqg0ny.data.model.PopularsResponse
import com.pickup.rsbyoqg0ny.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject
import com.github.pwittchen.infinitescroll.library.InfiniteScrollListener
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by inject()
    private lateinit var popularsAdapter: PopularsAdapter
    var binding: ActivityMainBinding? = null
    var MAX_ITEMS_PER_REQUEST = 20
    private var layoutManager: LinearLayoutManager? = null

    var counter = 2
    var calledPage = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel.getPopulars(1)
        load()
        observe()
    }

    private fun load() {
        viewModel.popularMovies.value = arrayListOf()
        popularsAdapter = PopularsAdapter(viewModel.popularMovies.value!!,
            object : PopularsAdapter.OnItemClickListener {
                override fun onItemClick(movie: Movie) {
                    showCustomDialog(movie)
                }

            })
        layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        binding?.rvPopulars?.layoutManager = layoutManager
        binding?.rvPopulars?.adapter = popularsAdapter
        binding?.rvPopulars?.addOnScrollListener(createInfiniteScrollListener())
    }

    private fun observe() {
        viewModel.popularsResponse.observe(this, Observer {
            var values = viewModel.popularMovies.value
            values?.addAll(it.results)
            viewModel.popularMovies.postValue(values)
        })

        viewModel.popularMovies.observe(this, Observer {
            popularsAdapter.notifyDataSetChanged()
            Handler(Looper.getMainLooper()).postDelayed({
                counter += 1
            }, 1000)
        })
    }


    private fun createInfiniteScrollListener(): InfiniteScrollListener {
        return object : InfiniteScrollListener(MAX_ITEMS_PER_REQUEST, layoutManager) {
            override fun onScrolledToEnd(firstVisibleItemPosition: Int) {
                if (counter != calledPage) {
                    calledPage = counter
                    viewModel.getPopulars(counter)
                }

            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun showCustomDialog(movie: Movie) {
        var customDialog = Dialog(this)
        customDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        customDialog?.setCancelable(true)
        customDialog?.setContentView(R.layout.popup_movie_detail)
        customDialog?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        (customDialog.findViewById(R.id.tv_name) as TextView).text = movie.name
        (customDialog.findViewById(R.id.tv_date) as TextView).text =
            getString(R.string.text_first_air_date) + movie.first_air_date
        (customDialog.findViewById(R.id.tv_popularity) as TextView).text =
            getString(R.string.text_popularity) + movie.popularity.toString()
        (customDialog.findViewById(R.id.tv_desc) as TextView).text = movie.overview
        (customDialog.findViewById(R.id.tv_country) as TextView).text =
            getString(R.string.text_language) + movie.original_language

        Picasso.with(this).load(getString(R.string.url_image_baseurl) + movie.poster_path).into(
            (customDialog.findViewById(R.id.iv_poster) as ImageView)
        )

        (customDialog.findViewById(R.id.iv_close) as ImageView).setOnClickListener {
            customDialog.dismiss()
        }


        val width = (resources.displayMetrics.widthPixels * 0.80).toInt()
        customDialog?.show()
        customDialog?.window?.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT)
    }
}
