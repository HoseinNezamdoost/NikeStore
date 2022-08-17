package com.hosein.nzd.nikestore.feature.main.productList

import androidx.lifecycle.MutableLiveData
import com.hosein.nzd.nikestore.R
import com.hosein.nzd.nikestore.common.NikeSingleObservable
import com.hosein.nzd.nikestore.common.NikeViewModel
import com.hosein.nzd.nikestore.data.Product
import com.hosein.nzd.nikestore.data.repository.ProductRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class ProductListViewModel(var sort: Int, private val productRepository: ProductRepository) :
    NikeViewModel() {

    val productListLiveData = MutableLiveData<List<Product>>()
    val selectItemSortTitle = MutableLiveData<Int>()
    val arrayItems = arrayOf(R.string.sortLatest,
        R.string.sortPopular,
        R.string.sortPriceHighToLow,
        R.string.sortPriceLowToHigh)

    init {
        getProduct()
    }

    fun getProduct() {
        selectItemSortTitle.value = arrayItems[sort]
        progressBraLiveData.value = true
        productRepository.getProduct(sort)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progressBraLiveData.value = false }
            .subscribe(object : NikeSingleObservable<List<Product>>(disposable) {
                override fun onSuccess(t: List<Product>) {
                    productListLiveData.value = t
                }
            })
    }

    fun selectedItemByUser(sort: Int) {
        this.sort = sort
        getProduct()
    }

}