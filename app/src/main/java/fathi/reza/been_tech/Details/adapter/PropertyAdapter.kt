package fathi.reza.been_tech.Details.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fathi.reza.been_tech.Data.Detail.Child
import fathi.reza.been_tech.R
import java.util.ArrayList
const val ITEM_PARENT = 1
const val ITEM_VAL = 2
const val ITEM_EMPTY = 3

class PropertyAdapter(val final_array:ArrayList<Any>) :RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ITEM_VAL) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_property_value, parent, false)
            return ViewHolder(view)
        }  else if (viewType == ITEM_EMPTY){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_empty, parent, false)
            return ViewHolderEmpty(view)
        } else{
            val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_property_title, parent, false)
            return ViewHolderParent(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (getItemViewType(position) == ITEM_VAL) {
            val Child=final_array[position] as Child

                (holder as ViewHolder).title.text = Child.title
                (holder as ViewHolder).value.text =Child.`val`

        }else if (getItemViewType(position) == ITEM_EMPTY){
            (holder as ViewHolderEmpty).titleEmpty.text ="-"
            (holder as ViewHolderEmpty).valueEmpty.text ="-"
        }else{
            (holder as ViewHolderParent).parent.text = final_array[position] as CharSequence?
        }
    }

    override fun getItemCount(): Int {
        return final_array.size
    }

    class ViewHolderParent(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val parent = itemView.findViewById<TextView>(R.id.txt_property_ParentBValue)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title = itemView.findViewById<TextView>(R.id.txt_property_showTitle)
        val value = itemView.findViewById<TextView>(R.id.txt_property_showValue)
    }

    class ViewHolderEmpty(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val titleEmpty = itemView.findViewById<TextView>(R.id.txt_property_TitleEmpty)
        val valueEmpty = itemView.findViewById<TextView>(R.id.txt_property_ValueEmpty)
    }

    override fun getItemViewType(position: Int): Int {

           return if (final_array[position] is String) {
               ITEM_PARENT
             }else if (final_array[position] is ArrayList<*>){
                    ITEM_EMPTY
             }else{
                 ITEM_VAL
             }
    }

}




