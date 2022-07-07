package fathi.reza.been_tech.Home

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import fathi.reza.been_tech.Data.Products
import fathi.reza.been_tech.Details.DetailsActivity
import fathi.reza.been_tech.Home.adapter.CatAdapter
import fathi.reza.been_tech.Home.adapter.ProductsAdapter
import fathi.reza.been_tech.Home.adapter.ViewpagerFragmentAdapter
import fathi.reza.been_tech.Home.viewModel.HomeViewModel
import fathi.reza.been_tech.LoginAndRegister.TokenHolder
import fathi.reza.been_tech.R
import fathi.reza.been_tech.Utills.MyFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class HomeFragment : MyFragment(),ProductsAdapter.OnProductItemClickListener {


    val homeViewModel: HomeViewModel by viewModel()
    val sharedPreferences:SharedPreferences by inject()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager=view.findViewById<ViewPager2>(R.id.viewPager_home_slider)
        val dotsIndicator=view.findViewById<DotsIndicator>(R.id.dots_indicator)
        val recyclerView=view.findViewById<RecyclerView>(R.id.rv_home_showCatItem)
        recyclerView.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        val rvAmazing=view.findViewById<RecyclerView>(R.id.rv_home_showAmazing)
        rvAmazing.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)

        homeViewModel.bannerLiveData.observe(viewLifecycleOwner){
            val viewpagerFragmentAdapter= ViewpagerFragmentAdapter(this,it)
            viewPager.adapter=viewpagerFragmentAdapter
            dotsIndicator.setViewPager2(viewPager)

        }


        homeViewModel.progressBarLiveData.observe(viewLifecycleOwner){
            showProgressBar(it)
        }
        homeViewModel.catLiveData.observe(viewLifecycleOwner){
            val catAdapter : CatAdapter by inject { parametersOf(it)}
            recyclerView.adapter=catAdapter

        }

        homeViewModel.productLiveData.observe(viewLifecycleOwner){ products->

            homeViewModel.defItemProductLiveData.observe(viewLifecycleOwner){defItem->

                val productsAdapter:ProductsAdapter by inject { parametersOf(products,defItem) }
                rvAmazing.adapter=productsAdapter
                productsAdapter.setProductListener(this)

            }

        }
        homeViewModel.checkTokenLiveData.observe(viewLifecycleOwner){

            if (it.status=="done"){
                sharedPreferences.edit().apply {
                    putString("token",it.message)
                    putString("refresh_token",it.refresh_token)
                }.apply()
            }
        }


    }

    override fun onProductClick(product: Products) {
        startActivity(Intent(context,DetailsActivity::class.java).apply {
            putExtra("id",product.id)
        })
    }


}