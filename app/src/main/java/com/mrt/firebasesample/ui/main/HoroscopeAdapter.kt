package com.mrt.firebasesample.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.mrt.firebasesample.data.FirebaseModel
import com.mrt.firebasesample.databinding.ItemHoroscopeBinding


class HoroscopeAdapter(
    private var horoscope: ArrayList<FirebaseModel.Sign?>,
    private val listener: OnItemClickListener
) : androidx.recyclerview.widget.RecyclerView.Adapter<HoroscopeAdapter.TripItemViewHolder>() {

    override fun onBindViewHolder(holder: TripItemViewHolder, position: Int) {
        return holder.bind(horoscope[position], listener)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripItemViewHolder =
        TripItemViewHolder.create(parent)


    override fun getItemCount() = horoscope.count()


    class TripItemViewHolder(private var binding: ItemHoroscopeBinding) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(
            sign: FirebaseModel.Sign?,
            listener: OnItemClickListener
        ) = with(binding) {
            Glide.with(binding.root.context)
                .load(sign?.imageUrl)
                .into(binding?.ivBg)

            binding.tvName.text = sign?.name
            itemView.setOnClickListener {
                sign?.let { it1 ->
                    listener.onItemClick(it1)
                }
            }
        }

        companion object {
            fun create(parent: ViewGroup?): TripItemViewHolder {
                val binding =
                    ItemHoroscopeBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
                return TripItemViewHolder(binding)
            }
        }
    }

    fun updatePeople(horoscope: ArrayList<FirebaseModel.Sign?>) {
        this.horoscope = horoscope

    }


    interface OnItemClickListener {
        fun onItemClick(item: FirebaseModel.Sign)
    }


}