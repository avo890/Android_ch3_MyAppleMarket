package com.example.myapplemarket

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplemarket.databinding.LayoutItemBinding
import java.text.DecimalFormat

class ProductAdapter (val productItem: MutableList<ProductItem>) : RecyclerView.Adapter<ProductAdapter.Holder>() {

    interface ProductClick {
        fun onClick(view : View, position : Int)
    }
    var productClick : ProductClick? = null

    interface ProductLongClick{
        fun onLongClick(view : View, position : Int)
    }
    var productLongClick : ProductLongClick? = null

    inner class Holder(val binding : LayoutItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val ivProduct = binding.ivProduct
        val tvPrice = binding.tvPrice
        val tvTitle = binding.tvTitle
        val tvLoca = binding.tvLoca
        val tvChat = binding.tvChat
        val tvLiked = binding.tvLiked
        val ivLiked = binding.ivLiked
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = LayoutItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  Holder(binding)
    }

    override fun getItemCount(): Int = productItem.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.itemView.setOnLongClickListener{
            productLongClick?.onLongClick(it,position)
            return@setOnLongClickListener true
        }

        holder.itemView.setOnClickListener{
            productClick?.onClick(it,position)
        }

        holder.ivProduct.setImageResource(productItem[position].ivProduct)
        holder.tvPrice.text = DecimalFormat("#,###").format(productItem[position].tvPrice) + "원"
        holder.tvTitle.text = productItem[position].tvTitle
        holder.tvLoca.text = productItem[position].tvLoca
        holder.tvChat.text = productItem[position].tvChat.toString()
        holder.tvLiked.text = productItem[position].tvLiked.toString()
        if(productItem[position].isLiked) {
            holder.ivLiked.setImageResource(R.drawable.item_resize_heart_filled)
        } else {
            holder.ivLiked.setImageResource(R.drawable.item_resize_heart)
        }
    }
}