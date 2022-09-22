package com.hosein.nzd.nikestore.feature.profile.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hosein.nzd.nikestore.R
import com.hosein.nzd.nikestore.common.implementSpringAnimationTrait
import com.hosein.nzd.nikestore.data.Product
import com.hosein.nzd.nikestore.services.loadImage.LoadImageService
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_favorite_product.view.*

class FavoriteAdapter(
    val listFavoriteProduct: MutableList<Product>,
    val imageService: LoadImageService,
    val clickItemFavorite: ClickItemFavorite,
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorite_product, parent, false))
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavoriteProduct[position])
    }

    override fun getItemCount(): Int {
        return listFavoriteProduct.size
    }

    fun deleteItem(product: Product) {
        val index = listFavoriteProduct.indexOf(product)
        listFavoriteProduct.removeAt(index)
        notifyItemRemoved(index)
    }

    inner class FavoriteViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(product: Product) {
            imageService.load(containerView.nikeImageView, product.image)
            containerView.productTitleTv.text = product.title

            containerView.implementSpringAnimationTrait()

            containerView.setOnClickListener {
                clickItemFavorite.onShortClick(product)
            }

            containerView.setOnLongClickListener {
                clickItemFavorite.onLongClick(product)
                return@setOnLongClickListener false
            }

        }
    }

    interface ClickItemFavorite {
        fun onShortClick(product: Product)

        fun onLongClick(product: Product)
    }

}