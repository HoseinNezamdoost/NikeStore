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
import kotlinx.android.synthetic.main.item_product.view.*

const val VIEW_TYPE_ROUND = 0
const val VIEW_TYPE_SMALL = 1
const val VIEW_TYPE_LARGE = 2

class MainProductAdapter(var viewType: Int = VIEW_TYPE_ROUND, val loadImageService: LoadImageService) : RecyclerView.Adapter<MainProductAdapter.MainProductViewHolder>() {

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

            if (product.isFavorite)
                itemView.favoriteBtn.setImageResource(R.drawable.ic_favorite_fill)
            else
                itemView.favoriteBtn.setImageResource(R.drawable.ic_favorites)

            itemView.favoriteBtn.setOnClickListener {
                product.isFavorite = !product.isFavorite
                onProductListClickListener?.onFavoriteClick(product)
                notifyItemChanged(adapterPosition)
            }

        }

    }

    override fun getItemViewType(position: Int): Int {
        return viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainProductViewHolder {

        val viewType = when(viewType){
            VIEW_TYPE_ROUND->R.layout.item_product
            VIEW_TYPE_SMALL->R.layout.item_product_small
            VIEW_TYPE_LARGE->R.layout.item_product_large
            else -> throw IllegalAccessException("ViewType can not is empty")
        }

        return MainProductViewHolder(LayoutInflater.from(parent.context).inflate(viewType , parent , false))
    }

    override fun onBindViewHolder(holder: MainProductViewHolder, position: Int) = holder.bindProduct(productsLast[position])

    override fun getItemCount(): Int = productsLast.size

    interface OnProductListClickListener{
        fun onClick(product: Product)
        fun onFavoriteClick(product: Product)
    }
}