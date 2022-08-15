package com.hosein.nzd.nikestore.data.repository

import com.hosein.nzd.nikestore.data.Comment
import com.hosein.nzd.nikestore.data.repository.source.CommentDataSource
import io.reactivex.rxjava3.core.Single

class CommentRepositoryImpl(val commentDataSource: CommentDataSource) : CommentRepository {

    override fun getAll(product_id:Int): Single<List<Comment>> = commentDataSource.getAll(product_id)

    override fun insert(): Single<Comment> {
        TODO("Not yet implemented")
    }
}