package com.tetomanta.mestonki.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tetomanta.mestonki.databinding.ItemPersonBinding
import com.tetomanta.mestonki.network.Person

class PeopleAdapter(
    private var people: MutableList<Person>
) : androidx.recyclerview.widget.RecyclerView.Adapter<PeopleAdapter.TripItemViewHolder>() {

    override fun onBindViewHolder(holder: TripItemViewHolder, position: Int) {
        return holder.bind(people[position])
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripItemViewHolder =
        TripItemViewHolder.create(parent)


    override fun getItemCount() = people.count()


    class TripItemViewHolder(private var binding: ItemPersonBinding) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(
            person: Person
        ) = with(binding) {

            binding.tvName.text = person.fullName+" ("+person.id+")"

        }

        companion object {
            fun create(parent: ViewGroup?): TripItemViewHolder {
                val binding =
                    ItemPersonBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
                return TripItemViewHolder(binding)
            }
        }
    }

    fun updatePeople( people: MutableList<Person>){
        this.people=people

    }



}