package com.mrt.firebasesample.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.mrt.firebasesample.R
import com.mrt.firebasesample.data.FirebaseModel
import com.mrt.firebasesample.databinding.ActivityHomeBinding
import com.mrt.firebasesample.ui.detail.DetailActivity
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by inject()
    private var horoscopeAdapter: HoroscopeAdapter? = null
     var binding: ActivityHomeBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        viewModel.login("test@test.com", "test1234") {
            if (it) {
                viewModel.getHoroscope()
            }
        }
        load()
        observe()
    }

    private fun load() {
        viewModel.horoscope.value = arrayListOf()

    }

    fun createAdapter() {
        horoscopeAdapter = HoroscopeAdapter(viewModel.horoscope.value!!,
            object : HoroscopeAdapter.OnItemClickListener {
                override fun onItemClick(item: FirebaseModel.Sign) {
                    DetailActivity.sign = item
                    val intent = Intent(this@MainActivity, DetailActivity::class.java)
                    startActivity(intent)
                }

            })
        binding?.rvHoroscope?.layoutManager = GridLayoutManager(this, 3)
        binding?.rvHoroscope?.adapter = horoscopeAdapter

    }

    private fun observe() {
        viewModel.horoscope.observe(this, Observer {
            runOnUiThread {

                if (it.size > 0 && horoscopeAdapter == null) {
                    createAdapter()

                    binding?.clBar?.visibility = View.GONE
                }
                horoscopeAdapter?.updatePeople(viewModel.horoscope.value!!)
                horoscopeAdapter?.notifyDataSetChanged()

            }


        })
        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }


}
