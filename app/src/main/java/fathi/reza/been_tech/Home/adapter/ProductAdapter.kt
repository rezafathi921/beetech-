package fathi.reza.been_tech.Home.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fathi.reza.been_tech.Custom.MyImageView
import fathi.reza.been_tech.Data.DefItemProduct
import fathi.reza.been_tech.Data.Products
import fathi.reza.been_tech.Home.ImageLoading
import fathi.reza.been_tech.R
import java.util.ArrayList


const val PRODUCT_DEF = 1
const val PRODUCT_ITEM = 2

class ProductsAdapter(
    val products: List<Products>,
    val imageLoading: ImageLoading,
    val defItemProducts: List<DefItemProduct>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var onProductItemClickListener:OnProductItemClickListener?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == PRODUCT_ITEM) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
            MyViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.product_def_item, parent, false)
            DefViewHolder(view)
        }
    }
    fun setProductListener( onProductItemClickListener: OnProductItemClickListener){
      this.onProductItemClickListener=onProductItemClickListener
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {



        if (getItemViewType(position) == PRODUCT_DEF) {
            val defitem = defItemProducts[0]
            imageLoading.load((holder as DefViewHolder).imgProduct_Def, defitem.pic_def)
        } else {
            val product = products[position-1]
            (holder as MyViewHolder).txt_discount.text = product.discount + "%"
            (holder as MyViewHolder).txt_greenPrice.text = product.persian_price_final
            (holder as MyViewHolder).txt_redPrice.text =
                Html.fromHtml("<strike>${product.price_first_use}</strike>")
            (holder as MyViewHolder).txt_productItem_tite.text = product.title
            imageLoading.load((holder as MyViewHolder).imgProducItem, product.pic)
            holder.itemView.setOnClickListener{
                    onProductItemClickListener!!.onProductClick(product)
            }
        }
    }

    override fun getItemCount(): Int = (products.size)+1

    class DefViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgProduct_Def =
            itemView.findViewById<MyImageView>(R.id.img_product_def_Item_showItem_def)
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt_discount = itemView.findViewById<TextView>(R.id.txt_productItem_discount)
        val txt_productItem_tite = itemView.findViewById<TextView>(R.id.txt_productItem_title)
        val txt_greenPrice = itemView.findViewById<TextView>(R.id.txt_productItm_greenPrice)
        val txt_redPrice = itemView.findViewById<TextView>(R.id.txt_productItem_pprice)
        val imgProducItem = itemView.findViewById<MyImageView>(R.id.img_productItem_showItem)

    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            PRODUCT_DEF
        } else {
            PRODUCT_ITEM
        }
    }
    interface OnProductItemClickListener{
        fun onProductClick(product:Products)
    }
}