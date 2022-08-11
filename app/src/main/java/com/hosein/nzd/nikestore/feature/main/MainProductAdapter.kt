package com.hosein.nzd.nikestore.feature.main

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hosein.nzd.nikestore.R
import com.hosein.nzd.nikestore.common.formatPrice
import com.hosein.nzd.nikestore.common.implementSpringAnimationTrait
import com.hosein.nzd.nikestore.data.Product
import com.hosein.nzd.nikestore.services.loadImage.LoadImageService
import com.hosein.nzd.nikestore.view.NikeImageView

class MainProductAdapter(val loadImageService: LoadImageService) : RecyclerView.Adapter<MainProductAdapter.MainProductViewHolder>() {

    var onProductListClickListener: OnProductListClickListener? = null

    var productsLast = ArrayList<Product>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    inner class MainProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageIv :NikeImageView = itemView.findViewById(R.id.productIv)
        val titleTv :TextView = itemView.findViewById(R.id.productTitleTv)
        val previousPriceTv :TextView = itemView.findViewById(R.id.previousPriceTv)
        val currentPriceTv :TextView = itemView.findViewById(R.id.currentPriceTv)


        fun bindProduct(product: Product){
            loadImageService.load(imageIv , product.image)
            titleTv.text = product.title
            currentPriceTv.text = formatPrice(product.price)

            previousPriceTv.text = formatPrice(product.previous_price)
            previousPriceTv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

            itemView.implementSpringAnimationTrait()
            itemView.setOnClickListener {
                onProductListClickListener?.onClick(product)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainProductViewHolder {
        return MainProductViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_product , parent , false))
    }

    override fun onBindViewHolder(holder: MainProductViewHolder, position: Int) = holder.bindProduct(productsLast[position])

    override fun getItemCount(): Int = productsLast.size

    interface OnProductListClickListener{
        fun onClick(product: Product)
    }
}