package fathi.reza.been_tech.Cart.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import fathi.reza.been_tech.Custom.MyImageView
import fathi.reza.been_tech.Data.Cart.CarrtItem
import fathi.reza.been_tech.Data.Cart.CartResponse
import fathi.reza.been_tech.Home.ImageLoading
import fathi.reza.been_tech.R
import fathi.reza.been_tech.Utills.PersianToEnglish
import fathi.reza.been_tech.Utills.currencyFormat


const val CART_ITEM = 1001
const val SEND_FREE_ITEM = 1002
const val BENEFIT_PLUSE_ITEM = 1003
const val PURCHASE_ITEM = 1004

class CartAdapter(val cartResponse: CartResponse, val imageLoading: ImageLoading) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onCartItemClickListener:OnCartItemClickListener?=null
    var totalDiscount:Float?=null
    var totalPrice:Long?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CART_ITEM -> CartItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cart_item_product, parent, false))
            SEND_FREE_ITEM -> SendFreeItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cart_item_free_sending, parent, false))
            BENEFIT_PLUSE_ITEM -> BenefitViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cart_item_benefit, parent, false))
            else -> PurchaseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cart_item_summary_caart, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            CART_ITEM -> {
                (holder as CartItemViewHolder).LinarFrameShowColorAndshowText.removeAllViews()
                holder.garanteeText.text = ""
                holder.FrameRLtakeNavigationSend.removeAllViews()
                Log.i("LOG", "onBindViewHolder:${cartResponse.message[position].count}")
                holder.numberCount.text=cartResponse.message[position].count
                val finalPriceProduct=(((cartResponse.message[position].price).toLong())*((cartResponse.message[position].count).toLong()))/10
                holder.productPrice.text =PersianToEnglish(currencyFormat(finalPriceProduct.toString()))
                imageLoading.load((holder as CartItemViewHolder).imageProduct,cartResponse.message[position].pic)
                holder.titleProduct.text = cartResponse.message[position].productTitle
                cartResponse.message[position].colors.forEach {
                    if (it.contains("#")) {

                        val withAndHghight = 18
                        val imgCircle = ImageView(holder.imageProduct.context)
                        val density = imgCircle.resources.displayMetrics.density
                        val withAndHghightFinal = withAndHghight * density.toInt()
                        val layoutParamImgCircle=RelativeLayout.LayoutParams(withAndHghightFinal, withAndHghightFinal)
                        imgCircle.setImageResource(R.drawable.ic_baseline_circle_24)
                        imgCircle.setColorFilter(Color.parseColor(it))
                        layoutParamImgCircle.setMargins(0, 0,(3 * density).toInt(), 0)
                        imgCircle.layoutParams = layoutParamImgCircle
                        holder.LinarFrameShowColorAndshowText.addView(imgCircle)

                    }
                    else {
                        val marginRhite = 8
                        val txtTitleColor = TextView(holder.imageProduct.context)
                        val layoutParams = RelativeLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                        val density = txtTitleColor.resources.displayMetrics.density
                        val marginRitheFinal = marginRhite * density.toInt()
                        layoutParams.setMargins(0, 0, marginRitheFinal, 0)
                        txtTitleColor.minLines = 1
                        txtTitleColor.maxLines = 1
                        txtTitleColor.setTextColor(Color.BLACK)
                        txtTitleColor.text = it
                        txtTitleColor.layoutParams = layoutParams
                        holder.LinarFrameShowColorAndshowText.addView(txtTitleColor)

                    }
                }
                cartResponse.message[position].garantee.forEach {
                    if (it.contains("بی تچ")) {
                        holder.imgGarantee.setColorFilter(Color.parseColor("#fb9450"))
                    }
                    holder.garanteeText.append(it+" ، ")
                }
                holder.companyText.text = cartResponse.message[position].companyname
                if (cartResponse.message[position].stock.contains("بی تچ")) {
                    holder.imgStock.setColorFilter(Color.parseColor("#fb9450"))
                }
                holder.txtInStcock.text = cartResponse.message[position].stock
                cartResponse.message[position].typesend.forEach {
                    val width = 3
                    val height = 15
                    val marginRight = 9
                    val wHCircle = 10
                    val relativeLayout = RelativeLayout(holder.imageProduct.context)
                    val layoutParamRelative = RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    relativeLayout.id = (0..100).random()
                    relativeLayout.layoutParams = layoutParamRelative
                    holder.FrameRLtakeNavigationSend.addView(relativeLayout)
                //////////////////////////////
                    val view = View(holder.imageProduct.context)
                    val density = view.resources.displayMetrics.density
                    val layoutParamView = RelativeLayout.LayoutParams(
                        width * density.toInt(),
                        height * density.toInt()
                    )
                    layoutParamView.setMargins(0, 0, (marginRight * density).toInt(), 0)
                    layoutParamView.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
                    val idView = (0..100).random()
                    view.id = idView
                    view.background = ContextCompat.getDrawable(holder.imageProduct.context,R.drawable.shape_for_line_cart_item)
                    view.layoutParams = layoutParamView
                    relativeLayout.addView(view)
               ////////////////////////
                    val imageCircle = ImageView(holder.imageProduct.context)
                    val densityImageCircle = imageCircle.resources.displayMetrics.density
                    val layoutParamImageCircle = RelativeLayout.LayoutParams(
                        wHCircle * densityImageCircle.toInt(),
                        wHCircle * densityImageCircle.toInt()
                    )
                    val imageCircleId = (0..100).random()
                    imageCircle.id = imageCircleId
                    layoutParamImageCircle.addRule(RelativeLayout.BELOW, idView)
                    layoutParamImageCircle.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
                    layoutParamImageCircle.setMargins(
                        0,
                        ((2 * densityImageCircle).toInt()),
                        ((7 * densityImageCircle).toInt()),
                        0
                    )
                    imageCircle.setImageResource(R.drawable.ic_baseline_circle_24)
                    imageCircle.layoutParams = layoutParamImageCircle
                    if (cartResponse.message[position].stock.contains("بی تچ")) {
                        imageCircle.setColorFilter(Color.parseColor("#fb9450"))
                    }
                    relativeLayout.addView(imageCircle)
              ////////////////////////////////////////
                    val typeSend = TextView(holder.imageProduct.context)
                    val DensityTypeSent = typeSend.resources.displayMetrics.density
                    val layoutParamTypeSend = RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    typeSend.id = (0..100).random()
                    typeSend.setTextColor(Color.BLACK)
                    typeSend.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10F)
                    layoutParamTypeSend.addRule(RelativeLayout.BELOW, idView)
                    layoutParamTypeSend.setMargins(0, 0, ((8 * DensityTypeSent).toInt()), 0)
                    layoutParamTypeSend.addRule(RelativeLayout.LEFT_OF, imageCircleId)
                    typeSend.layoutParams = layoutParamTypeSend
                    typeSend.text = it
                    relativeLayout.addView(typeSend)
                }
                if (cartResponse.message[position].count.toInt()==cartResponse.message[position].tedad_mojood.toInt()){
                    holder.maxItem.visibility=View.VISIBLE
                }else{
                    holder.maxItem.visibility=View.INVISIBLE
                }
                if (cartResponse.message[position].count.toInt() > 1 ){
                    holder.btnTrash.visibility=View.INVISIBLE
                    holder.imgDecreaseCount.visibility=View.VISIBLE
                }else{
                    holder.btnTrash.visibility=View.VISIBLE
                    holder.imgDecreaseCount.visibility=View.INVISIBLE
                }
                if (cartResponse.message[position].progressBar){
                    holder.progressBarLoadingCount.visibility = View.VISIBLE
                    holder.numberCount.visibility = View.INVISIBLE
                }else{
                    holder.progressBarLoadingCount.visibility = View.INVISIBLE
                    holder.numberCount.visibility = View.VISIBLE
                }

                holder.imgIncreaseCount.setOnClickListener{
                    if (cartResponse.message[position].count.toInt()<cartResponse.message[position].tedad_mojood.toInt()) {
                        onCartItemClickListener!!.onInCreaseClick(cartResponse.message[position])
                    }
                }

                holder.imgDecreaseCount.setOnClickListener{
                    if (cartResponse.message[position].count.toInt() > 1){
                        onCartItemClickListener!!.onDecreaseClick(cartResponse.message[position])
                    }
                }

                holder.titleProduct.setOnClickListener{
                    onCartItemClickListener!!.onItemClick(cartResponse.message[position])
                }

                holder.btnTrash.setOnClickListener{
                    onCartItemClickListener!!.onDeleteItemClick(cartResponse.message[position])
                }

            }
            SEND_FREE_ITEM -> {
                if(totalPrice!! > 3000000){
                   ( holder as SendFreeItemViewHolder).rySendFree.visibility=View.VISIBLE
                }else{
                    ( holder as SendFreeItemViewHolder).rySendFree.visibility=View.GONE
                }
            }
            BENEFIT_PLUSE_ITEM -> {

                (holder as BenefitViewHolder).thumbnailProductCart.removeAllViews()
                (holder as BenefitViewHolder).frameRL.removeAllViews()
                val allBenefitArray = mutableListOf<String>()
                var myCountBenefitProductInTextView = 0
                for (i in cartResponse.message.indices) {
                    cartResponse.message[i].benefitplus.forEach {
                        allBenefitArray.add(it)
                        if (it == "امکان ارسال فوری(شهر تهران)") {
                            val picProduct = cartResponse.message[i].pic
                            val widthAndHeight = 50
                            val marginRightMyImageView = 4
                            val myimageView =MyImageView((holder as BenefitViewHolder).imgRedPint.context)
                            val densityMyimageView = myimageView.resources.displayMetrics.density
                            val layoutParamsMYImsgeViewBenefit = RelativeLayout.LayoutParams((widthAndHeight * densityMyimageView).toInt(),(widthAndHeight * densityMyimageView).toInt())
                            myimageView.id = (0..20000).random()
                            layoutParamsMYImsgeViewBenefit.setMargins(0,0,(marginRightMyImageView * densityMyimageView).toInt(),0)
                            myimageView.setImageURI(picProduct)
                            myimageView.layoutParams = layoutParamsMYImsgeViewBenefit
                            if (holder.thumbnailProductCart.childCount < 5) {
                                holder.thumbnailProductCart.addView(myimageView)
                            } else {
                                myCountBenefitProductInTextView++
                            }
                        }
                    }
                }
                if (myCountBenefitProductInTextView > 0) {
                    val widthAndHeightMyCountBenfitProduct = 50
                    val marginRightMyCountBenfitProduct = 4
                    val myCountBenefitProduct =
                        TextView((holder as BenefitViewHolder).imgRedPint.context)
                    myCountBenefitProduct.text =
                        myCountBenefitProductInTextView.toString() + "+ کالا"
                    myCountBenefitProduct.gravity = Gravity.CENTER_VERTICAL
                    myCountBenefitProduct.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10F)
                    myCountBenefitProduct.id = (0..20000).random()
                    myCountBenefitProduct.setTextColor(ContextCompat.getColor(holder.imgRedPint.context,R.color.black)
                    )
                    val densityMyCountBenefitProduct =myCountBenefitProduct.resources.displayMetrics.density
                    val layoutParamsMyCountBenfitProduct = RelativeLayout.LayoutParams((widthAndHeightMyCountBenfitProduct * densityMyCountBenefitProduct).toInt(),(widthAndHeightMyCountBenfitProduct * densityMyCountBenefitProduct).toInt())
                    layoutParamsMyCountBenfitProduct.setMargins(0,0,(marginRightMyCountBenfitProduct * densityMyCountBenefitProduct).toInt(),0)
                    myCountBenefitProduct.layoutParams = layoutParamsMyCountBenfitProduct
                    holder.thumbnailProductCart.addView(myCountBenefitProduct)
                }

                val b = allBenefitArray.distinctBy { it }
                b.forEach {

                    val widthLine = 3
                    val heightLine = 15
                    val marginRight = 8
                    val wHCircle = 10
                    /////////////////////
                    val relativeLayout =
                        RelativeLayout((holder as BenefitViewHolder).imgRedPint.context)
                    relativeLayout.id = (0..10000).random()
                    val densityRL = relativeLayout.resources.displayMetrics.density
                    val layoutParamsRL = RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    layoutParamsRL.setMargins(
                        (8 * densityRL).toInt(),
                        0,
                        (12 * densityRL).toInt(),
                        0
                    )
                    relativeLayout.layoutParams = layoutParamsRL
                    (holder as BenefitViewHolder).frameRL.addView(relativeLayout)
                    //////////////////////////////
                    val view = View(holder.imgRedPint.context)
                    val density = view.resources.displayMetrics.density
                    val params = RelativeLayout.LayoutParams(
                        widthLine * density.toInt(),
                        heightLine * density.toInt()
                    )
                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
                    params.setMargins(0, (1 * density).toInt(), (marginRight * density).toInt(), 0)
                    val idView = (0..100000).random()
                    view.id = idView
                    view.backgroundTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            holder.imgRedPint.context,
                            R.color.greenIcon
                        )
                    )
                    view.background = ContextCompat.getDrawable(
                        holder.imgRedPint.context,
                        R.drawable.shape_for_line_cart_item
                    )
                    view.layoutParams = params
                    relativeLayout.addView(view)
                    ///////////////////////////
                    val imageCircle = ImageView(holder.imgRedPint.context)
                    val densityImageCircle = imageCircle.resources.displayMetrics.density
                    val layoutParamCircle = RelativeLayout.LayoutParams(
                        wHCircle * densityImageCircle.toInt(),
                        wHCircle * densityImageCircle.toInt()
                    )
                    val imageCircleId = (0..1000000).random()
                    imageCircle.id = imageCircleId
                    layoutParamCircle.addRule(RelativeLayout.BELOW, idView)
                    layoutParamCircle.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
                    layoutParamCircle.setMargins(
                        0,
                        ((2 * densityImageCircle).toInt()),
                        ((6 * densityImageCircle).toInt()),
                        0
                    )
                    imageCircle.setImageResource(R.drawable.ic_baseline_circle_24)
                    imageCircle.imageTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            holder.imgRedPint.context,
                            R.color.purple_500
                        )
                    )
                    imageCircle.layoutParams = layoutParamCircle
                    relativeLayout.addView(imageCircle)
                    ///////////////////////////
                    val benefitPlus = TextView(holder.imgRedPint.context)
                    val layoutParambenefitPlus = RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    val DensitybenefitPlus = benefitPlus.resources.displayMetrics.density
                    benefitPlus.id = (0..1000000).random()
                    benefitPlus.setTextColor(Color.BLACK)
                    benefitPlus.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10F)
                    layoutParambenefitPlus.addRule(RelativeLayout.BELOW, idView)
                    layoutParambenefitPlus.setMargins(0, 0, ((8 * DensitybenefitPlus).toInt()), 0)
                    layoutParambenefitPlus.addRule(RelativeLayout.LEFT_OF, imageCircleId)
                    benefitPlus.text = it
                    benefitPlus.layoutParams = layoutParambenefitPlus
                    relativeLayout.addView(benefitPlus)
                    ////////////////////////////////


                }
            }
            else -> {
                (holder as PurchaseViewHolder).totalItemCart.text =cartResponse.message.size.toString() + "کالا"
                val allPriceCartToman = totalPrice?.div(10)
                holder.allPrice.text = PersianToEnglish(currencyFormat(allPriceCartToman.toString()))
                val allDiscountT= totalDiscount?.div(10)
                holder.allDiscount.text= PersianToEnglish(currencyFormat(allDiscountT.toString()))
                if (allPriceCartToman != null) {
                    holder.endPriceWhiteDiscount.text= PersianToEnglish(currencyFormat((allPriceCartToman- allDiscountT!!).toString()))
                }

            }
        }

    }

    override fun getItemCount(): Int = (cartResponse.message.size) + 3


    class CartItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageProduct = itemView.findViewById<MyImageView>(R.id.img_cart_showProduct)
        val titleProduct = itemView.findViewById<TextView>(R.id.txt_cartItem_showTitleProduct)
        val imgGarantee = itemView.findViewById<ImageView>(R.id.img_cart_garantee)
        val garanteeText = itemView.findViewById<TextView>(R.id.txt_cartItem_garanteeText)
        val productPrice=itemView.findViewById<TextView>(R.id.txt_cartItem_showPrice)
        val companyText = itemView.findViewById<TextView>(R.id.txt_cartItem_CompanyText)
        val imgStock = itemView.findViewById<ImageView>(R.id.img_cart_existing)
        val txtInStcock = itemView.findViewById<TextView>(R.id.txt_cartItem_inStcock)
        val maxItem=itemView.findViewById<TextView>(R.id.txt_cartItem_MaxExist)
        val FrameRLtakeNavigationSend =
            itemView.findViewById<LinearLayout>(R.id.AllFrameRL_cartItem_takeNavigationSend)
        val imgIncreaseCount = itemView.findViewById<ImageView>(R.id.img_countItem_add)
        val numberCount = itemView.findViewById<TextView>(R.id.txt_carItem_count)
        val imgDecreaseCount = itemView.findViewById<ImageView>(R.id.img_cartItem_mines)
        val progressBarLoadingCount =
            itemView.findViewById<ProgressBar>(R.id.progressBar_CartItem_LoadingCount)
        val btnTrash = itemView.findViewById<ImageView>(R.id.img_countItem_remove)
        var LinarFrameShowColorAndshowText =
            itemView.findViewById<LinearLayout>(R.id.frame_cartItem_showColorAndname)

    }

    class PurchaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val totalItemCart = itemView.findViewById<TextView>(R.id.txt_cartItem_Total_p)
        val allPrice = itemView.findViewById<TextView>(R.id.txt_cartItem_showAllPrice)
        val allDiscount = itemView.findViewById<TextView>(R.id.txt_cartItem_showAllDiscount)
        val endPriceWhiteDiscount =itemView.findViewById<TextView>(R.id.txt_cartItem_showALLPriceWithDiscount)
    }

    class BenefitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val frameRL =itemView.findViewById<LinearLayout>(R.id.frame_cartIte_showLineAndCircleAndTextBenefit)
        val imgRedPint = itemView.findViewById<ImageView>(R.id.img_cartItem_sidePint)
        val thumbnailProductCart =itemView.findViewById<LinearLayout>(R.id.frame_cartItem_thubnailProduct)
    }

    class SendFreeItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rySendFree=itemView.findViewById<RelativeLayout>(R.id.ry_cartItem_sendree)
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            ((cartResponse.message.size) + 2) -> PURCHASE_ITEM
            ((cartResponse.message.size) + 1) -> BENEFIT_PLUSE_ITEM
            cartResponse.message.size -> SEND_FREE_ITEM
            else -> CART_ITEM
        }
    }

    fun changeChartItemCount(cartItem:CarrtItem,increase:Boolean){
        if(increase){
            var currentCount=cartItem.count.toInt()
            val newCount=currentCount+1
            val cartItems:MutableList<CarrtItem> = cartResponse.message as MutableList<CarrtItem>
            val index=cartItems.indexOf(cartItem)
            cartItem.count=newCount.toString()
            cartItems[index] = cartItem
            notifyDataSetChanged()
            cartItem.progressBar=false
        }else {
            var currentCount=cartItem.count.toInt()
            val newCount=currentCount-1
            val cartItems:MutableList<CarrtItem> = cartResponse.message as MutableList<CarrtItem>
            val index=cartItems.indexOf(cartItem)
            cartItem.count=newCount.toString()
            cartItems[index] = cartItem
            notifyDataSetChanged()
            cartItem.progressBar=false
        }
        }
    }



interface OnCartItemClickListener{
   fun onInCreaseClick(cartItem:CarrtItem)
   fun onDecreaseClick(cartItem:CarrtItem)
   fun onItemClick(cartItem:CarrtItem)
   fun onDeleteItemClick(cartItem:CarrtItem)
}