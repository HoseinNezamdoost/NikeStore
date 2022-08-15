package com.hosein.nzd.nikestore.feature.main.productActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hosein.nzd.nikestore.R
import com.hosein.nzd.nikestore.data.Comment

class ProductAdapterComment(val showAll : Boolean = false) : RecyclerView.Adapter<ProductAdapterComment.ViewHolderComment>() {

    var comments = ArrayList<Comment>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    class ViewHolderComment(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var commentTitle = itemView.findViewById<TextView>(R.id.commentTitleTv)
        var commentAuthor = itemView.findViewById<TextView>(R.id.commentAuthor)
        var commentDate = itemView.findViewById<TextView>(R.id.commentDateTv)
        var commentContent = itemView.findViewById<TextView>(R.id.commentContentTv)

        fun bindComment(comment: Comment){
            commentTitle.text = comment.title
            commentAuthor.text = comment.author.email
            commentDate.text = comment.date
            commentContent.text = comment.content
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolderComment {
        return ViewHolderComment(LayoutInflater.from(p0.context).inflate(R.layout.item_comment , p0 , false))
    }

    override fun onBindViewHolder(p0: ViewHolderComment, p1: Int) {
        p0.bindComment(comments[p1])
    }

    override fun getItemCount(): Int {
        return if (comments.size > 5 && !showAll) 5 else comments.size
    }

}