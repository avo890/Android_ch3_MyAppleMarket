package com.example.myapplemarket

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductItem(
    val ivProduct:Int,
    val tvPrice:Int,
    val tvTitle:String,
    val tvDescription : String,
    val tvSeller : String,
    val tvLoca:String,
    var tvLiked:Int,
    val tvChat:Int,
    var isLiked:Boolean
) : Parcelable
