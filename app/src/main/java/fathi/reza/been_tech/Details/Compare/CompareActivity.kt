package fathi.reza.been_tech.Details.Compare



import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fathi.reza.been_tech.Custom.MyImageView
import fathi.reza.been_tech.Data.Detail.Child
import fathi.reza.been_tech.Data.Detail.ParentAndChild
import fathi.reza.been_tech.Details.Compare.adapter.CompareAdapter
import fathi.reza.been_tech.Details.Compare.viewmodel.CompareViewModel
import fathi.reza.been_tech.Home.ImageLoading
import fathi.reza.been_tech.R
import fathi.reza.been_tech.Utills.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class CompareActivity : MyActivity() {
    private val compareViewModel: CompareViewModel by inject { parametersOf(intent.extras!!.getString(SECOND_PRODUCT)) }
    private val compareAdapter: CompareAdapter by inject()
    private val imageLoading:ImageLoading by inject ()
    var firstFinalProduct= mutableListOf<Any>()
    private lateinit var  firstProductProperty :ArrayList<Any>
    private var productTitle: String? = null
    var productPrice: String? = null
    var productPic: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compare)

        val imgFirstProduct=findViewById<MyImageView>(R.id.img_compare_firstProduct)
        val titleFirstProduct=findViewById<TextView>(R.id.txt_compare_firstProductTitle)
        val priceFirstProduct=findViewById<TextView>(R.id.txt_compare_firstProductPrice)
        val imgSecondProduct=findViewById<MyImageView>(R.id.img_compare_secondProduct)
        val titleSecondProduct=findViewById<TextView>(R.id.txt_compare_secondProductTitle)
        val priceSecondProduct=findViewById<TextView>(R.id.txt_compare_secondProductPrice)
        val close=findViewById<FrameLayout>(R.id.btn_compare_close)
        val btnAddProduct=findViewById<Button>(R.id.btn_compare_addProduct)
        val firstFrame=findViewById<LinearLayout>(R.id.ln_compare_first)
        val secondFrame=findViewById<LinearLayout>(R.id.ln_compare_second)
        val txtToolbar=findViewById<TextView>(R.id.txt_property_tiltleToolbar)
        val btnBack=findViewById<ImageView>(R.id.img_property_backward)
        txtToolbar.text="مقایسه محصول"


        val rv = findViewById<RecyclerView>(R.id.rv_compareActivity)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter=compareAdapter
        val dividerItemDecoration=DividerItemDecoration(rv.context,DividerItemDecoration.VERTICAL).apply {
            ContextCompat.getDrawable(rv.context,R.drawable.divider_bottom)?.let { setDrawable(it) }
        }
        rv.addItemDecoration(dividerItemDecoration)

        val intent = intent
        if (intent != null) {
            val propertyFirstProduct:ArrayList<ParentAndChild>? = intent.extras!!.getParcelableArrayList(FIRST_PRODUCT)
            firstProductProperty= mixParentAndChild(propertyFirstProduct)
            productTitle = intent.extras!!.getString(FIRST_PRODUCT_TITLE)
            productPrice = intent.extras!!.getString(FIRST_PRODUCT_PRICE)
            productPic = intent.extras!!.getString(FIRST_PRODUCT_PIC)
        }


        productPic?.let { imageLoading.load(imgFirstProduct, it) }
        titleFirstProduct.text=productTitle
        priceFirstProduct.text=productPrice
        compareViewModel.secondPropertyProductCommon.observe(this,{
            imageLoading.load(imgSecondProduct,it[1])
            titleSecondProduct.text=it[2]
            priceSecondProduct.text=it[0]
        })


        compareViewModel.progressBarLiveData.observe(this) {
            showProgressBar(it)
        }
        compareViewModel.secondPropertyProduct.observe(this) {
          val  propertySecondProduct = it as ArrayList<ParentAndChild>
            val secondProductProperty= mixParentAndChild(propertySecondProduct)
            compareAdapter.secondProductProperty=secondProductProperty
            for (i in firstProductProperty.indices) {
                when (firstProductProperty[i]){
                    is String->{
                        val firstProductParent= firstProductProperty[i] as CharSequence
                        firstFinalProduct.add(firstProductParent)
                    }

                    is ArrayList<*>->{
                        val arrayList= firstProductProperty[i] as ArrayList<*>
                        //firstFinalProduct.add(arrayList)
                        val parentArrayList=firstProductProperty[i-1]
                        var indexParentArrayListInSecondArray=secondProductProperty.indexOf(parentArrayList)
                        while ((indexParentArrayListInSecondArray+1)<secondProductProperty.size && secondProductProperty[indexParentArrayListInSecondArray+1] is Child){
                            val kid=secondProductProperty[indexParentArrayListInSecondArray+1] as Child
                            firstFinalProduct.add(Child(kid.idproduct,kid.title,"-"))
                            indexParentArrayListInSecondArray++
                        }
                    }
                    is Child-> {
                        val kid1 = firstProductProperty[i] as Child
                        if (firstProductProperty[i - 1] is String) {
                            firstFinalProduct.add(kid1)
                            var nextChildInFirstProductProperty = i + 1
                            loop@ do {
                                if (firstProductProperty[nextChildInFirstProductProperty] is Child){
                                    firstFinalProduct.add(firstProductProperty[nextChildInFirstProductProperty] as Child)
                                }else{
                                    break@loop
                                }
                                if(nextChildInFirstProductProperty+1<firstProductProperty.size) {
                                    nextChildInFirstProductProperty++
                                }else{
                                    break@loop
                                }
                            } while (firstProductProperty[nextChildInFirstProductProperty] is Child)
                        }

                        val kid1Title=kid1.title
                        var y = 0
                        do {
                            val x = i - 1-y
                            y++
                        } while (firstProductProperty[x] !is String)
                        val parentChildInFirstProduct = firstProductProperty[i - y] as CharSequence
                        var indexParentChildInSecondArray=secondProductProperty.indexOf(parentChildInFirstProduct)
                       while ((indexParentChildInSecondArray+1)<secondProductProperty.size && secondProductProperty[indexParentChildInSecondArray+1] is Child ){
                            val kid2=secondProductProperty[indexParentChildInSecondArray+1] as Child
                            val kid2Title =  kid2.title
                            if (kid1Title!=kid2Title) {
                                var isExistence=false
                                loop@for (baby in firstFinalProduct){
                                    if (baby is Child){
                                        val babyTitleInFinalArray=baby.title
                                        if (kid2Title==babyTitleInFinalArray){
                                            isExistence=true
                                            break@loop
                                        }
                                    }
                                }

                                if (!isExistence) {
                                    firstFinalProduct.add(Child(kid2.idproduct, kid2.title, "-"))
                                }
                            }

                                indexParentChildInSecondArray++


                        }

                    }
                }
            }
            compareAdapter.firstProductProperty= firstFinalProduct as ArrayList<Any>

        }

        close.setOnClickListener{
        compareAdapter.removeSecondProductProperty()
            firstFrame.visibility=View.GONE
            secondFrame.visibility=View.VISIBLE


        }
        btnAddProduct.setOnClickListener{
            finish()
        }
        btnBack.setOnClickListener{
            finish()
        }









    }
}