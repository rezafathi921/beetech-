package fathi.reza.been_tech.Cat.adapter

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fathi.reza.been_tech.Custom.MyImageView
import fathi.reza.been_tech.Data.CatFragment.AllCat
import fathi.reza.been_tech.Data.CatFragment.FirstLevel
import fathi.reza.been_tech.Data.CatFragment.SecondLevel
import fathi.reza.been_tech.Home.ImageLoading
import fathi.reza.been_tech.R

class AllCatItemAdapter(val imageLoading: ImageLoading) : RecyclerView.Adapter<AllCatItemAdapter.AllCatViewHolder>() {

        var secondLevel = mutableListOf<SecondLevel>()
            set(value) {
                field=value
                notifyDataSetChanged()
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllCatViewHolder {

        return AllCatViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_catfragment_showallcatitem,parent,false))
    }

    override fun onBindViewHolder(holder: AllCatViewHolder, position: Int) {
        val oneSecondLevel=secondLevel[position]
        holder.setupView(imageLoading,oneSecondLevel)

    }

    override fun getItemCount(): Int =secondLevel.size

    class AllCatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img=itemView.findViewById<MyImageView>(R.id.img_catFragment_showSecondLevel)
        val titleSecond=itemView.findViewById<TextView>(R.id.txt_catFragment_secondlevelName)
        val totallProduct=itemView.findViewById<TextView>(R.id.txt_catFragment_showCountProduct)

        fun setupView(imageLoading: ImageLoading,secondLevel: SecondLevel){
        imageLoading.load(img,secondLevel.icon)
            titleSecond.text=secondLevel.title
            totallProduct.text=secondLevel.totalProducts.toString() +  " کالا "
        }
    }
}