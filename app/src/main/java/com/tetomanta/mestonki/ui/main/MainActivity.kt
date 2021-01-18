package com.tetomanta.mestonki.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.tetomanta.mestonki.R
import com.tetomanta.mestonki.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject
import androidx.recyclerview.widget.LinearLayoutManager


class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by inject()
    private var usersAdapter: PeopleAdapter? = null
    var binding: ActivityMainBinding? = null
    private var layoutManager: LinearLayoutManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel.getPeoplePart()
        load()
        observe()
    }

    private fun load() {
        viewModel.people.value = arrayListOf()

    }

    fun createAdapter() {
        usersAdapter = PeopleAdapter(viewModel.people.value!!)
        layoutManager = LinearLayoutManager(this)
        binding?.rvUsers?.layoutManager = layoutManager
        binding?.rvUsers?.adapter = usersAdapter

        binding?.nsvPage?.viewTreeObserver
            ?.addOnScrollChangedListener {
                if (binding?.nsvPage!!.getChildAt(0).getBottom()
                    <= (binding?.nsvPage!!.height + binding?.nsvPage!!.scrollY)
                ) {
                    binding?.clBar?.visibility = VISIBLE
                    viewModel.getPeoplePart()

                }
            };
    }

    private fun observe() {
        viewModel.people.observe(this, Observer {

            if (it.size > 0 && usersAdapter == null) {
                createAdapter()
            } else {
                binding?.clBar?.visibility = GONE
            }
            usersAdapter?.updatePeople(viewModel.people.value!!)
            usersAdapter?.notifyDataSetChanged()
            Handler(Looper.getMainLooper()).postDelayed({
            }, 1000)

            if (viewModel.people.value?.size ?: 0 < 17) {
                viewModel.getPeoplePart()
            }
        })

        viewModel.error.observe(this, Observer {
            viewModel.getPeoplePart()
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }


}
