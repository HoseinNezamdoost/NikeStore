package com.hosein.nzd.nikestore.feature.cart

import androidx.lifecycle.MutableLiveData
import com.hosein.nzd.nikestore.R
import com.hosein.nzd.nikestore.common.NikeSingleObservable
import com.hosein.nzd.nikestore.common.NikeViewModel
import com.hosein.nzd.nikestore.data.*
import com.hosein.nzd.nikestore.data.repository.CartRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers

class CartFragmentViewModel(private val cartRepository: CartRepository) : NikeViewModel() {

    val cartItemLiveData = MutableLiveData<List<CartItem>>()
    val cartPerchesLiveData = MutableLiveData<PerchesDetail>()
    val cartEmptyStateLiveData = MutableLiveData<EmptyState>()

    private fun getCartList() {

        if (!TokenContainer.accessToken.isNullOrEmpty()) {

            progressBraLiveData.value = true
            cartEmptyStateLiveData.value = EmptyState(false)

            cartRepository.get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { progressBraLiveData.value = false }
                .subscribe(object : NikeSingleObservable<CartResponse>(disposable) {
                    override fun onSuccess(t: CartResponse) {
                        if (t.cart_items.isNotEmpty()) {
                            cartItemLiveData.value = t.cart_items
                            cartPerchesLiveData.value =
                                PerchesDetail(t.payable_price, t.shipping_cost, t.total_price)
                        } else
                            cartEmptyStateLiveData.value = EmptyState(true, R.string.cartEmptyState)
                    }
                })

        } else {
            cartEmptyStateLiveData.value = EmptyState(true, R.string.cartEmptyState, true)
        }
    }

    fun removeCart(cartItem: CartItem): Completable {
        return cartRepository.remove(cartItem.cart_item_id)
            .doAfterSuccess {
                calculatePerchesPrice()
                cartItemLiveData.value?.let {
                    if (it.isEmpty()) {
                        cartEmptyStateLiveData.postValue(EmptyState(true, R.string.cartEmptyState))
                    }
                }
            }
            .ignoreElement()
    }

    fun increaseCartItemCount(cartItem: CartItem): Completable {
        return cartRepository.changeCount(cartItem.cart_item_id, ++cartItem.count)
            .doAfterSuccess { calculatePerchesPrice() }
            .ignoreElement()
    }

    fun decreaseCartItemCount(cartItem: CartItem): Completable {
        return cartRepository.changeCount(cartItem.cart_item_id, --cartItem.count)
            .doAfterSuccess { calculatePerchesPrice() }
            .ignoreElement()
    }

    fun refresh() {
        getCartList()
    }

    private fun calculatePerchesPrice() {
        cartPerchesLiveData.value?.let { perchesCart ->
            var totalPrice = 0
            var payablePrice = 0

            cartItemLiveData.value?.let { listItemCart ->
                listItemCart.forEach {
                    totalPrice += it.product.price * it.count
                    payablePrice += (it.product.price - it.product.discount) * it.count
                }
            }
            perchesCart.total_price = totalPrice
            perchesCart.payable_price = payablePrice
            cartPerchesLiveData.postValue(perchesCart)
        }
    }

}