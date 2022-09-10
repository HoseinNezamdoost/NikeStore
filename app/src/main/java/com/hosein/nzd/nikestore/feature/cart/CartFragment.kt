package com.hosein.nzd.nikestore.feature.cart

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hosein.nzd.nikestore.R
import com.hosein.nzd.nikestore.common.EXTRA_KEY_ID
import com.hosein.nzd.nikestore.common.EXTRA_PASS
import com.hosein.nzd.nikestore.common.NikeCompletableObservable
import com.hosein.nzd.nikestore.common.NikeFragment
import com.hosein.nzd.nikestore.data.CartItem
import com.hosein.nzd.nikestore.data.PerchesCart
import com.hosein.nzd.nikestore.feature.auth.AuthActivity
import com.hosein.nzd.nikestore.feature.main.productActivity.ProductDetailActivity
import com.hosein.nzd.nikestore.services.loadImage.LoadImageService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.item_cart.*
import kotlinx.android.synthetic.main.view_cart_empty_state.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment : NikeFragment(), CartFragmentAdapter.OnClickCartItemChild {

    val viewModel: CartFragmentViewModel by viewModel()
    lateinit var adapter: CartFragmentAdapter
    val loadImageService: LoadImageService by inject()
    val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.progressBraLiveData.observe(viewLifecycleOwner) {
            setProgressIndicator(it)
        }

        viewModel.cartItemLiveData.observe(viewLifecycleOwner) {
            cartItemsRv.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = CartFragmentAdapter(it as MutableList<CartItem>, loadImageService, this)
            cartItemsRv.adapter = adapter
        }

        viewModel.cartPerchesLiveData.observe(viewLifecycleOwner) { perchesCart ->
            adapter.let {
                it.perchesCart = perchesCart
                adapter.notifyItemChanged(adapter.cartItems.size)
            }
        }

        viewModel.cartEmptyStateLiveData.observe(viewLifecycleOwner) {
            if (it.mustShow) {
                val emptyState = showEmptyState(R.layout.view_cart_empty_state)
                emptyState?.let {view->
                    emptyStateMessageTv.text = getString(it.textEmptyState)
                    emptyStateCtaBtn.visibility = if (it.mustShowCallToAction) View.VISIBLE else View.GONE
                    emptyStateCtaBtn.setOnClickListener {
                        startActivity(Intent(requireContext() , AuthActivity::class.java))
                    }
                }
            }
        }

    }

    override fun onStart() {
        super.onStart()
        viewModel.refresh()
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

    override fun onClickRemoveFromCartBtn(cartItem: CartItem) {
        viewModel.removeCart(cartItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeCompletableObservable(compositeDisposable) {
                override fun onComplete() {
                    adapter.remove(cartItem)
                }
            })
    }

    override fun onClickDecreaseBtn(cartItem: CartItem) {

        if (cartItem.count == 0) {
            decreaseBtn.isSelected = false
            showSnackBar("تعداد این محصول صفر است")
        } else {
            viewModel.decreaseCartItemCount(cartItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NikeCompletableObservable(compositeDisposable) {
                    override fun onComplete() {
                        adapter.decries(cartItem)
                        cartItem.showProgressChangeItemCount = false
                    }
                })
        }
    }

    override fun onClickIncreaseBtn(cartItem: CartItem) {

        if (cartItem.count == 5) {
            decreaseBtn.isSelected = false
            showSnackBar("تعداد این محصول نمیتواند بیشتر از 5 عدد باشد")
        } else {
            viewModel.increaseCartItemCount(cartItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NikeCompletableObservable(compositeDisposable) {
                    override fun onComplete() {
                        adapter.increase(cartItem)
                        cartItem.showProgressChangeItemCount = false
                    }
                })
        }

    }

    override fun onClickContainerView(cartItem: CartItem) {
        startActivity(Intent(requireContext(), ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_ID, cartItem.product)
        })
    }

}