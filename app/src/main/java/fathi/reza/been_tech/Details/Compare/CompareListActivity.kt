package fathi.reza.been_tech.Details.Compare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fathi.reza.been_tech.Data.Detail.CompareList
import fathi.reza.been_tech.Data.Detail.ParentAndChild
import fathi.reza.been_tech.Details.Compare.adapter.CompareListAdapter
import fathi.reza.been_tech.Details.Compare.viewmodel.ComprableViewModel
import fathi.reza.been_tech.R
import fathi.reza.been_tech.Utills.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class CompareListActivity : MyActivity(),CompareListAdapter.SetOnClickItemCompareListener {

    val comprableViewModel:ComprableViewModel by inject { parametersOf(intent.extras!!) }
    val adapter :CompareListAdapter by inject()
    var propertyCorrectProduct:ArrayList<ParentAndChild>?=null
    var productTitle:String?=null
    var productPrice:String?=null
    var productPic:String?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compare_list)

       val intent=intent
        if (intent!=null){
            this.propertyCorrectProduct= intent.extras!!.getParcelableArrayList(CORRECT_ITEM_PROPERTY)
             productTitle=intent.extras!!.getString(FIRST_PRODUCT_TITLE)
             productPrice=intent.extras!!.getString(FIRST_PRODUCT_PRICE)
             productPic=intent.extras!!.getString(FIRST_PRODUCT_PIC)
        }

        val rv=findViewById<RecyclerView>(R.id.rv_compareList)
        rv.layoutManager =LinearLayoutManager(this)

        comprableViewModel.progressBarLiveData.observe(this,{
            showProgressBar(it)
        })

        comprableViewModel.compareListLiveData.observe(this){
           rv.adapter=adapter
            adapter.set(this)
            adapter.compareLists= it as MutableList<CompareList>
        }
    }

    override fun onItemClick(compareList: CompareList) {

        val bundle = Bundle().apply {
            putParcelableArrayList(FIRST_PRODUCT,propertyCorrectProduct)
        }
        val intent = Intent(applicationContext, CompareActivity::class.java).apply {
            putExtras(bundle)
            putExtra(SECOND_PRODUCT,compareList.id)
            putExtra(FIRST_PRODUCT_PIC,productPic)
            putExtra(FIRST_PRODUCT_TITLE,productTitle)
            putExtra(FIRST_PRODUCT_PRICE,productPrice)
        }
        startActivity(intent)

    }
}