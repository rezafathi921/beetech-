package fathi.reza.been_tech.Cart

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fathi.reza.been_tech.Cart.adapter.CartAdapter
import fathi.reza.been_tech.Cart.adapter.OnCartItemClickListener
import fathi.reza.been_tech.Cart.viewModel.CartItemViewModel
import fathi.reza.been_tech.Data.Cart.CarrtItem
import fathi.reza.been_tech.LoginAndRegister.LoginActivity
import fathi.reza.been_tech.R
import fathi.reza.been_tech.Utills.MyCompletableObserver
import fathi.reza.been_tech.Utills.MyFragment
import fathi.reza.been_tech.Utills.completableHelper
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class CartFragment : MyFragment(),OnCartItemClickListener {
    val cartItemViewModel:CartItemViewModel by viewModel()
    var cartItemAdapter:CartAdapter?=null
    val compositeDisposable=CompositeDisposable()

    override fun onStart() {
        super.onStart()
        cartItemViewModel.getCartList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val recyclerView=view.findViewById<RecyclerView>(R.id.rv_CartFragment)
        val btnPay=view.findViewById<Button>(R.id.btn_cart_pay)
        recyclerView.layoutManager=LinearLayoutManager(context)
        btnPay.setOnClickListener{
            val intent=Intent(Intent.ACTION_VIEW, Uri.parse("http://clicksite.org/app_digi_mellat/example.php"))
            startActivity(intent)
        }

        cartItemViewModel.cartResponseLiveData.observe(viewLifecycleOwner){
            Log.i("LOG", "onViewCreated:$it ")
            val cartAdapter:CartAdapter by inject { parametersOf(it)}
            cartItemAdapter=cartAdapter
            recyclerView.adapter=cartAdapter
            cartAdapter.onCartItemClickListener=this@CartFragment
        }
        cartItemViewModel.totalPriceLiveData.observe(viewLifecycleOwner){
            cartItemAdapter?.let { cartItemAdapter->
                cartItemAdapter.totalPrice=it
                cartItemAdapter.notifyDataSetChanged()
            }
        }
        cartItemViewModel.totalDiscountLiveData.observe(viewLifecycleOwner){
            cartItemAdapter?.let { cartItemAdapter->
                cartItemAdapter.totalDiscount=it
                cartItemAdapter.notifyDataSetChanged()

            }
        }

        cartItemViewModel.progressBarLiveData.observe(viewLifecycleOwner){
            showProgressBar(it)
        }
        cartItemViewModel.emptyStateLiveData.observe(viewLifecycleOwner) {
            val parent=view.findViewById<ConstraintLayout>(R.id.emptyState)
            if (it.show){
               val emptyState= showEmptyState(R.layout.empty_state)
                emptyState?.let { view->
                    val txtEmptyState = view.findViewById<TextView>(R.id.txt_emptyState)
                    val btnEmptyState = view.findViewById<Button>(R.id.btn_emptyState)

                    txtEmptyState.text = getString(it.message)

                    if (it.button) {
                        btnEmptyState.visibility = View.VISIBLE
                    } else {
                        btnEmptyState.visibility = View.INVISIBLE
                    }
                    btnEmptyState.setOnClickListener{
                        startActivity(Intent(context,LoginActivity::class.java))
                    }

                }

            }else{

                parent?.let {
                  //  it.visibility=View.GONE
                }
            }
        }

    }

    override fun onInCreaseClick(cartItem: CarrtItem) {
        cartItemAdapter?.changeChartItemCount(cartItem,true)
        var count=cartItem.count.toInt()
        val newCount=count++
        cartItemViewModel.changeCartItemCount(cartItem,newCount,true)
            .completableHelper()
            .subscribe(object : MyCompletableObserver(compositeDisposable){
                override fun onComplete() {

                }

            })
    }

    override fun onDecreaseClick(cartItem: CarrtItem) {
        cartItemAdapter?.changeChartItemCount(cartItem,false)
        var count=cartItem.count.toInt()
        val newCount=count--
        cartItemViewModel.changeCartItemCount(cartItem,newCount,false)
            .completableHelper()
            .subscribe(object : MyCompletableObserver(compositeDisposable){
                override fun onComplete() {

                }

            })
    }

    override fun onItemClick(cartItem: CarrtItem) {
        TODO("Not yet implemented")
    }

    override fun onDeleteItemClick(cartItem: CarrtItem) {
        cartItemViewModel.removeCartItem(cartItem)
            .completableHelper()
            .subscribe(object : MyCompletableObserver(compositeDisposable) {
                override fun onComplete() {
                    Log.i("LOG", "onComplete: ")
                }


            })
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

}