package fathi.reza.been_tech.Details.Compare.adapter




import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fathi.reza.been_tech.Data.Detail.Child
import fathi.reza.been_tech.R


const val ITEM_PARENT = 1
const val ITEM_VAL = 2

class CompareAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var firstProductProperty = ArrayList<Any>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var secondProductProperty = ArrayList<Any>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun removeSecondProductProperty(){
        secondProductProperty.clear()
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_PARENT) {
           ParentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_property_title, parent, false))
        } else {
           ValueViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.compare_item, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position)== ITEM_PARENT){
            (holder as ParentViewHolder).title.text =firstProductProperty[position] as CharSequence?

        }else{
          val kid=firstProductProperty[position] as Child
            val kidTitle=kid.title
            (holder as ValueViewHolder).firstProduct.text="${kid.title} : ${kid.`val`}"
           loop@ for (i in secondProductProperty){
                if(i is Child){
                    val childTitle=(i as Child).title
                    if (kidTitle==childTitle){
                        (holder as ValueViewHolder).secondProduct.text="${i.title} : ${i.`val`}"
                        break@loop
                    }
                }
            }
            if ((holder as ValueViewHolder).secondProduct.text.isEmpty() || secondProductProperty.size==0) {
                (holder as ValueViewHolder).secondProduct.text = "-"
            }


        }
    }

    override fun getItemCount(): Int =firstProductProperty.size

    class ParentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title = itemView.findViewById<TextView>(R.id.txt_property_ParentBValue)
    }
    class ValueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val firstProduct = itemView.findViewById<TextView>(R.id.txt_compare_firstProduct)
        val secondProduct = itemView.findViewById<TextView>(R.id.txt_compare_secondProduct)
    }

    override fun getItemViewType(position: Int): Int {
        return if (firstProductProperty[position] is String){
            ITEM_PARENT
        }else{
            ITEM_VAL
        }
    }

}

