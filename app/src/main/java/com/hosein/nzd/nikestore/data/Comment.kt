package com.hosein.nzd.nikestore.data

data class Comment(
    var author: Author,
    var content: String,
    var date: String,
    var id: Int,
    var title: String
)