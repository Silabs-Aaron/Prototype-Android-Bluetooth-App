package com.example.prototypebluetoothapp

import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.prototypebluetoothapp.databinding.RecyclerviewDeviceBinding

class RecyclerViewAdapter(private val data: List<DataModel>): RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>(){


    inner class MyViewHolder(val binding: RecyclerviewDeviceBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: DataModel) {
            binding.devicelist = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)


        val listItemBinding = RecyclerviewDeviceBinding.inflate(inflater, parent, false)
       return MyViewHolder(listItemBinding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position])
    }
}