package fathi.reza.been_tech.Home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fathi.reza.been_tech.Custom.MyImageView
import fathi.reza.been_tech.Data.Cats
import fathi.reza.been_tech.Home.ImageLoading
import fathi.reza.been_tech.R

class CatAdapter(val CatList: List<Cats>,val imageLoading: ImageLoading) : RecyclerView.Adapter<CatAdapter.CatViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.cat_item,parent,false)
        return CatViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
       val item=CatList[position]
        imageLoading.load(holder.imgCatItemHome,item.icon)
        holder.catItemTitle.text=item.title

    }

    override fun getItemCount(): Int = CatList.size

    class CatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imgCatItemHome =itemView.findViewById<MyImageView>(R.id.img_CatItem_showImage)
        val catItemTitle= itemView.findViewById<TextView>(R.id.txt_CatItem_title)

    }
}