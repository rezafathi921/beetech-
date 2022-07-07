package fathi.reza.been_tech.Details.Compare.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fathi.reza.been_tech.Custom.MyImageView
import fathi.reza.been_tech.Data.Detail.CompareList
import fathi.reza.been_tech.Home.ImageLoading
import fathi.reza.been_tech.R

class CompareListAdapter(val imageLoading: ImageLoading): RecyclerView.Adapter<CompareListAdapter.ViewHolder>() {

    var setOnClickItemCompareListener: SetOnClickItemCompareListener?=null
    var compareLists:MutableList<CompareList> = ArrayList()
    set(value) {
        field=value
        notifyDataSetChanged()

    }

    fun set(setOnClickItemCompareListener: SetOnClickItemCompareListener){
        this.setOnClickItemCompareListener=setOnClickItemCompareListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.comparelist_item,parent,false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item= compareLists[position]
        holder.setupView(item,imageLoading)
        holder.itemView.setOnClickListener {
            setOnClickItemCompareListener!!.onItemClick(item)
        }
    }

    override fun getItemCount(): Int =compareLists.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pic=itemView.findViewById<MyImageView>(R.id.img_ComareList_showImage)
        val price=itemView.findViewById<TextView>(R.id.txt_compareAdapter_price)
        val title=itemView.findViewById<TextView>(R.id.title_compareListAdapter_showTitle)

        fun setupView(compareList: CompareList,imageLoading: ImageLoading){
                imageLoading.load(pic,compareList.pic)
                price.text=compareList.price
                title.text=compareList.title
        }

    }
    interface SetOnClickItemCompareListener{
        fun onItemClick(compareList: CompareList)
    }
}