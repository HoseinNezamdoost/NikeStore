package com.hosein.nzd.nikestore.data.repository.source

import com.hosein.nzd.nikestore.data.Comment
import io.reactivex.rxjava3.core.Single

interface CommentDataSource {

    fun getAll(product_id:Int) : Single<List<Comment>>

    fun insert() : Single<Comment>
}