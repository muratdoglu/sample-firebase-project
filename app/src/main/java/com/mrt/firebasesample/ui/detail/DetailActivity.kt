package com.mrt.firebasesample.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.mrt.firebasesample.R
import com.mrt.firebasesample.data.FirebaseModel
import com.mrt.firebasesample.databinding.ActivitySignDetailBinding

class DetailActivity : AppCompatActivity() {
    var binding: ActivitySignDetailBinding? = null

    companion object {
        var sign: FirebaseModel.Sign? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_detail)

        load()
    }

    private fun load() {
        binding?.ivLogo?.let {
            Glide.with(this)
                .load(sign?.imageUrl)
                .into(it)
        }
        binding?.tvName?.text = sign?.name
        binding?.tvDescription?.text = sign?.description

    }
}