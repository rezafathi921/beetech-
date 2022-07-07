package fathi.reza.been_tech.Details.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar
import fathi.reza.been_tech.Data.Detail.RatingItem
import fathi.reza.been_tech.R
import java.util.ArrayList

class ProductRatingItemAdapter(val ratingItems: ArrayList<RatingItem>):RecyclerView.Adapter<ProductRatingItemAdapter.ViewHolderRating>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderRating {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.layout_roundedd_cornerr,parent,false)
        return ViewHolderRating(view)
    }

    override fun onBindViewHolder(holder: ViewHolderRating, position: Int) {
        holder.ratingTitle.text=ratingItems[position].title
        holder.roundecCornerProgressBar.progress=ratingItems[position].value.toFloat()
    }

    override fun getItemCount(): Int =ratingItems.size

    class ViewHolderRating(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ratingTitle=itemView.findViewById<TextView>(R.id.txt_detail_titleRatingItem)
        val roundecCornerProgressBar=itemView.findViewById<RoundCornerProgressBar>(R.id.view_detain_roundCornerProgressbar)
    }
}