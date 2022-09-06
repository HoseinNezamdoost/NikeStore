package com.hosein.nzd.nikestore.feature.cart

import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hosein.nzd.nikestore.R
import com.hosein.nzd.nikestore.common.formatPrice
import com.hosein.nzd.nikestore.common.implementSpringAnimationTrait
import com.hosein.nzd.nikestore.data.CartItem
import com.hosein.nzd.nikestore.data.PerchesCart
import com.hosein.nzd.nikestore.services.loadImage.LoadImageService
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_cart.view.*
import kotlinx.android.synthetic.main.item_purchase_details.view.*

const val CART_ITEM = 0
const val CART_PERCHES = 1

class CartFragmentAdapter(
    val cartItems: MutableList<CartItem>,
    val loadImageService: LoadImageService,
    val onClickCartItemChild: OnClickCartItemChild,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var perchesCart: PerchesCart? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {

            CART_ITEM -> return CartItemViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_cart, parent, false))

            CART_PERCHES -> return CartPerchesViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_purchase_details, parent, false))

            else -> return CartItemViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_cart, parent, false))

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CartItemViewHolder -> holder.bindCartItem(cartItems[position])

            is CartPerchesViewHolder -> perchesCart?.let {
                holder.bindPerchesItem(it)
            }
        }
    }

    override fun getItemCount(): Int = cartItems.size + 1


    override fun getItemViewType(position: Int): Int {
        if (position == cartItems.size)
            return CART_PERCHES
        else
            return CART_ITEM
    }

    fun remove(cartItem: CartItem) {
        val index = cartItems.indexOf(cartItem)
        if (index > -1) {
            cartItems.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun increase(cartItem: CartItem) {
        val index = cartItems.indexOf(cartItem)
        if (index > -1){
            cartItems[index].showProgressChangeItemCount = true
            notifyItemChanged(index)
        }
    }

    fun decries(cartItem: CartItem) {
        val index = cartItems.indexOf(cartItem)
        if (index > -1){
            cartItems[index].showProgressChangeItemCount = true
            notifyItemChanged(index)
        }
    }

    inner class CartItemViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindCartItem(cartItem: CartItem) {

            containerView.productTitleTv.text = cartItem.product.title
            containerView.previousPriceTv.text = formatPrice(cartItem.product.price)
            containerView.priceTv.text = formatPrice(cartItem.product.price-cartItem.product.discount)
            loadImageService.load(containerView.productIv, cartItem.product.image)
            containerView.cartItemCountTv.text = cartItem.count.toString()

            containerView.changeCountProgressBar.visibility = if (cartItem.showProgressChangeItemCount) View.VISIBLE else View.GONE
            containerView.cartItemCountTv.visibility = if (cartItem.showProgressChangeItemCount) View.INVISIBLE else View.VISIBLE

            containerView.removeFromCartBtn.setOnClickListener {
                onClickCartItemChild.onClickRemoveFromCartBtn(cartItem)
            }

            containerView.decreaseBtn.setOnClickListener {
                onClickCartItemChild.onClickDecreaseBtn(cartItem)
                cartItem.showProgressChangeItemCount = true
                containerView.changeCountProgressBar.visibility = View.VISIBLE
                containerView.cartItemCountTv.visibility = View.INVISIBLE
            }

            containerView.increaseBtn.setOnClickListener {
                onClickCartItemChild.onClickIncreaseBtn(cartItem)
                cartItem.showProgressChangeItemCount = true
                containerView.changeCountProgressBar.visibility = View.VISIBLE
                containerView.cartItemCountTv.visibility = View.INVISIBLE
            }

            containerView.setOnClickListener {
                onClickCartItemChild.onClickContainerView(cartItem)
            }
            containerView.implementSpringAnimationTrait()
        }
    }

    inner class CartPerchesViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindPerchesItem(perchesCart: PerchesCart) {
            containerView.totalPriceTv.text = formatPrice(perchesCart.total_price)
            containerView.shippingCostTv.text = formatPrice(perchesCart.shipping_cost)
            containerView.payablePriceTv.text = formatPrice(perchesCart.payable_price)
        }
    }

    interface OnClickCartItemChild {
        fun onClickRemoveFromCartBtn(cartItem: CartItem)
        fun onClickDecreaseBtn(cartItem: CartItem)
        fun onClickIncreaseBtn(cartItem: CartItem)
        fun onClickContainerView(cartItem: CartItem)
    }

}