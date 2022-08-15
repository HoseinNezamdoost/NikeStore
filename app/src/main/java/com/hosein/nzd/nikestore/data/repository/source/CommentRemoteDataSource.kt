package com.hosein.nzd.nikestore.data.repository.source

import com.hosein.nzd.nikestore.data.Comment
import com.hosein.nzd.nikestore.services.http.ApiService
import io.reactivex.rxjava3.core.Single

class CommentRemoteDataSource(val apiService: ApiService) : CommentDataSource {

    override fun getAll(product_id:Int): Single<List<Comment>> = apiService.getComment(product_id)

    override fun insert(): Single<Comment> {
        TODO("Not yet implemented")
    }
}