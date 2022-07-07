package fathi.reza.been_tech.Details


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.text.Html
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.gson.JsonArray
import fathi.reza.been_tech.Custom.MyImageView
import fathi.reza.been_tech.Data.Detail.ParentAndChild
import fathi.reza.been_tech.Data.Detail.RatingItem
import fathi.reza.been_tech.Data.LoginAndRegister.LoginMessage
import fathi.reza.been_tech.Details.Chart.PriceHistoryActivity
import fathi.reza.been_tech.Details.Compare.CompareActivity
import fathi.reza.been_tech.Details.Compare.CompareListActivity
import fathi.reza.been_tech.Details.adapter.ProductRatingItemAdapter
import fathi.reza.been_tech.Details.viewModel.DetailViewModel
import fathi.reza.been_tech.Home.ImageLoading
import fathi.reza.been_tech.LoginAndRegister.LoginActivity
import fathi.reza.been_tech.LoginAndRegister.TokenHolder
import fathi.reza.been_tech.LoginAndRegister.viewModel.LoginViewModel
import fathi.reza.been_tech.R
import fathi.reza.been_tech.Utills.*
import io.reactivex.disposables.CompositeDisposable
import org.json.JSONArray
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.ArrayList


class DetailsActivity() : MyActivity(),BottomSheetDilaogMoreDetail.SetOnMenuItemClickListenerr {

    val detailViewModel: DetailViewModel by viewModel { parametersOf(intent.extras!!.getString("id")) }
    val imageLoading: ImageLoading by inject()
    val compositeDisposable=CompositeDisposable()
    val loginViewModel:LoginViewModel by inject()
    var parentAndChildForCompareActivity:ArrayList<ParentAndChild>?=null
    val ratinItemList=ArrayList<RatingItem>()
    var productId:String?=null
    var productCat:String?=null
    var productTitle:String?=null
    var productPrice:String?=null
    var productPic:String?=null
    val productRatingItemAdapter: ProductRatingItemAdapter by inject{ parametersOf(ratinItemList)}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val imgProduct = findViewById<MyImageView>(R.id.img_details_showpicProduct)
        val txt_title = findViewById<TextView>(R.id.txt_detail_showTitle)
        val waranty = findViewById<TextView>(R.id.txt_details_waranty)
        val more=findViewById<ImageView>(R.id.img_detail_more)
        val addToFavorit=findViewById<ImageView>(R.id.img_detail_addToFavorite)
        addToFavorit.contentDescription="white"
        val back = findViewById<ImageView>(R.id.img_detail_back)
        val addToBascket=findViewById<MaterialButton>(R.id.btn_detail_addToBasket)
        val pPrice = findViewById<TextView>(R.id.txt_detail_pPrice)
        val price = findViewById<TextView>(R.id.txt_detail_final_price)
        val color = findViewById<TextView>(R.id.txt_details_color)
        val detailIntroduction = findViewById<TextView>(R.id.txt_details_introduction)
        val ratingBar = findViewById<RatingBar>(R.id.details_ratingBar_showratingProduct)
        val properties_showDetails =findViewById<RelativeLayout>(R.id.properties_details_showDetails)
        val recyClerView = findViewById<RecyclerView>(R.id.rv_details_ratingProduct)
        recyClerView.layoutManager = LinearLayoutManager(this)

        addToBascket.setOnClickListener {
            detailViewModel.addToBasket(productId!!).singleHelper()
                .subscribe(object : MySingleObserver<LoginMessage>(compositeDisposable){
                    override fun onSuccess(t: LoginMessage) {
                        if (t.status == "success") {
                            Toast.makeText(
                                this@DetailsActivity,
                                "محصول با موفقیت به سبد خریداضافه شد",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })
        }

        detailViewModel.detailLiveData.observe(this) {
            productId=it.id
            productCat=it.idcategory
            productTitle=it.title
            productPic=it.pic
            parentAndChildForCompareActivity= it.parent_and_child as ArrayList<ParentAndChild>
            val rating_items = it.rating_item
            val jsonArray = JSONArray(rating_items)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val ratinItem =RatingItem(jsonObject.getString("title"), jsonObject.getString("value"))
                ratinItemList.add(ratinItem)
            }
            addToFavorit.setOnClickListener {
                if (loginViewModel.checkLoginLivedataStatus.value==false){
                    startActivity(Intent(applicationContext,LoginActivity::class.java))
                    finish()
                }else{
                    if (addToFavorit.contentDescription=="white"){
                        loginViewModel.addToFav(productId!!)

                    }else{
                        loginViewModel.deleteFromFav(productId!!)
                    }
                }
            }

            loginViewModel.statusAddOrDeleteToFav.observe(this) {
                if (it.status == "error") {
                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_LONG).show()
                } else if (it.status == "add") {
                    addToFavorit.setImageResource(R.drawable.ic_baseline_favorite_24)
                    addToFavorit.contentDescription = "red"
                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_LONG).show()
                } else {
                    addToFavorit.setImageResource(R.drawable.ic_round_favorite_border_24)
                    addToFavorit.contentDescription = "white"
                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_LONG).show()
                }

            }


            detailViewModel.favArrayInDetailLiveData.observe(this) {
                if (it.isNullOrEmpty()) {
                    addToFavorit.setImageResource(R.drawable.ic_round_favorite_border_24)
                    addToFavorit.contentDescription = "white"
                } else {
                    it.forEach {
                        if (it.idproduct == productId) {
                            addToFavorit.setImageResource(R.drawable.ic_baseline_favorite_24)
                            addToFavorit.contentDescription = "red"
                        }
                    }
                }
            }

            recyClerView.adapter=productRatingItemAdapter

            imageLoading.load(imgProduct, it.pic)
            txt_title.text = it.title
            waranty.text = it.garantee
            color.text = it.colors
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                detailIntroduction.text =
                    Html.fromHtml(it.introduction, Html.FROM_HTML_MODE_COMPACT)
            } else {
                detailIntroduction.text = Html.fromHtml(it.introduction)
            }

            ratingBar.rating = it.rating.toFloat()
            pPrice.text = PersianToEnglish(it.price) + "تومان"
            pPrice.paintFlags = android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
            price.text =PersianToEnglish(((((100 - (it.discount.toInt())) * ((it.price).toInt())) / 100).toString())) + "تومان"
            productPrice= PersianToEnglish(((((100 - (it.discount.toInt())) * ((it.price).toInt())) / 100).toString())) + "تومان"

        }

        properties_showDetails.setOnClickListener {
            val extras = Bundle().apply {
                putParcelableArrayList("listparentAndChild",parentAndChildForCompareActivity )
            }
            val intent = Intent(applicationContext, PropertyActivity::class.java).apply {
                putExtras(extras)
            }
            startActivity(intent)
        }

        detailViewModel.progressBarLiveData.observe(this) {
            showProgressBar(it)
        }
        back.setOnClickListener {
            finish()
        }
        more.setOnClickListener{
            val bottomSheetDilaogMoreDetail=BottomSheetDilaogMoreDetail()
            bottomSheetDilaogMoreDetail.show(supportFragmentManager,null)
            bottomSheetDilaogMoreDetail.setOnItemClick(this)
        }
    }

    override fun onMenuClick(itemType: String) {
       when(itemType){

           SHARE -> {
               val intent=Intent(Intent.ACTION_SEND).apply {
                   type="text/plain"
                   putExtra(Intent.EXTRA_TEXT,"https://www.digikala.com/product/dkp-6460974/%DA%AF%D9%88%D8%B4%DB%8C-%D9%85%D9%88%D8%A8%D8%A7%DB%8C%D9%84-%D8%A7%D9%BE%D9%84-%D9%85%D8%AF%D9%84-iphone-13-pro-max-a2413-%D8%AF%D9%88-%D8%B3%DB%8C%D9%85-%DA%A9%D8%A7%D8%B1%D8%AA-%D8%B8%D8%B1%D9%81%DB%8C%D8%AA-256-%DA%AF%DB%8C%DA%AF%D8%A7%D8%A8%D8%A7%DB%8C%D8%AA-%D9%88-%D8%B1%D9%85-6-%DA%AF%DB%8C%DA%AF%D8%A7%D8%A8%D8%A7%DB%8C%D8%AA")
               }
                  startActivity(Intent.createChooser(intent,"معرفی محصول"))
           }
           COMPARE -> {
               val bundle = Bundle().apply {
                   putParcelableArrayList(CORRECT_ITEM_PROPERTY, parentAndChildForCompareActivity)
               }
               val intent = Intent(applicationContext, CompareListActivity::class.java).apply {
                   putExtras(bundle)
                   putExtra(DATA,productCat)
                   putExtra(FIRST_PRODUCT_PIC,productPic)
                   putExtra(FIRST_PRODUCT_TITLE,productTitle)
                   putExtra(FIRST_PRODUCT_PRICE,productPrice)

               }

               startActivity(intent)

           }
           CHART -> {
               startActivity(Intent(applicationContext,PriceHistoryActivity::class.java).apply {
                   putExtra(DATA,productId)

               })
           }
       }
    }

    override fun onResume() {
        super.onResume()
        loginViewModel.checkLogin()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

}
