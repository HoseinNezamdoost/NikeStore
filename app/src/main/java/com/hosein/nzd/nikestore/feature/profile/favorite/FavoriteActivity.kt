package com.hosein.nzd.nikestore.feature.profile.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hosein.nzd.nikestore.R
import com.hosein.nzd.nikestore.common.EXTRA_KEY_ID
import com.hosein.nzd.nikestore.common.NikeActivity
import com.hosein.nzd.nikestore.common.NikeCompletableObservable
import com.hosein.nzd.nikestore.data.Product
import com.hosein.nzd.nikestore.feature.main.productActivity.ProductDetailActivity
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.view_default_empty_state.*
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteActivity : NikeActivity() , FavoriteAdapter.ClickItemFavorite {

    val viewModel : FavoriteViewModel by viewModel()
    val disposable = CompositeDisposable()
    lateinit var favoriteAdapter : FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        toolbarFavorite.onBackButtonToolbarNk = View.OnClickListener {
            finish()
        }

        helpBtn.setOnClickListener {
            showSnackBar(getString(R.string.helpFavorite))
        }

        viewModel.favoriteProductLiveData.observe(this){
            if (it.isNotEmpty()){
                favoriteProductsRv.layoutManager = LinearLayoutManager(this , RecyclerView.VERTICAL , false)
                favoriteAdapter = FavoriteAdapter(it as MutableList<Product>, get() , this)
                favoriteProductsRv.adapter = favoriteAdapter

            }else{
                showEmptyState(R.layout.view_default_empty_state)
                emptyStateMessageTv.text = getString(R.string.emptyStateFavorite)
            }

        }

    }

    override fun onShortClick(product: Product) {
        startActivity(Intent(this , ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_ID , product)
        })
    }

    override fun onLongClick(product: Product) {
        viewModel.delete(product)
            .subscribe(object : NikeCompletableObservable(disposable){
            override fun onComplete() {
                Log.i("TAG", "onCompleteFavorite: ")
                favoriteAdapter.deleteItem(product)
            }
        })
    }
}