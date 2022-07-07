package fathi.reza.been_tech.Cart.viewModel


import androidx.lifecycle.MutableLiveData
import fathi.reza.been_tech.Cart.repo.ICartItemRpo
import fathi.reza.been_tech.Data.Cart.CarrtItem
import fathi.reza.been_tech.Data.Cart.CartResponse
import fathi.reza.been_tech.Data.CartItemCount
import fathi.reza.been_tech.Data.EmptyState
import fathi.reza.been_tech.LoginAndRegister.TokenHolder
import fathi.reza.been_tech.R
import fathi.reza.been_tech.Utills.BaseViewModel
import fathi.reza.been_tech.Utills.MySingleObserver
import fathi.reza.been_tech.Utills.singleHelper
import io.reactivex.Completable
import org.greenrobot.eventbus.EventBus


class CartItemViewModel(val iCartItemRpo: ICartItemRpo): BaseViewModel() {
    val cartResponseLiveData= MutableLiveData<CartResponse>()
    val totalPriceLiveData= MutableLiveData<Long?>()
    val emptyStateLiveData=MutableLiveData<EmptyState>()
    val totalDiscountLiveData=MutableLiveData<Float>()

    init {
       getCartList()
    }

fun removeCartItem(carrtItem: CarrtItem):Completable{
    return  iCartItemRpo.removeCartItemFromBasket(carrtItem.product_id).doAfterSuccess {
        val cartItems:MutableList<CarrtItem> = cartResponseLiveData.value!!.message as MutableList<CarrtItem>
        val index=cartItems.indexOf(carrtItem)
        cartItems.removeAt(index)
        cartResponseLiveData.value!!.message=cartItems
         val count=EventBus.getDefault().getStickyEvent(CartItemCount::class.java)
        count?.let {
            it.count-=carrtItem.count.toInt()
            EventBus.getDefault().postSticky(it)
        }
        cartResponseLiveData.value?.let {
            if (it.message.isEmpty()) {
                emptyStateLiveData.postValue(EmptyState(true,R.string.emptyState_emptyBasket))
            }
        }

        calculatePrice()
    }.ignoreElement()
}
    fun changeCartItemCount(cartItem: CarrtItem,count:Int,isIncrease:Boolean):Completable{
        return iCartItemRpo.changeCartItemCart(cartItem.product_id,count).doAfterSuccess {
            val cartItems:MutableList<CarrtItem> = cartResponseLiveData.value!!.message as MutableList<CarrtItem>
            val index=cartItems.indexOf(cartItem)
            cartItem.count=count.toString()
            cartItems[index] = cartItem
            cartResponseLiveData.value!!.message=cartItems
            val cartItemCount=EventBus.getDefault().getStickyEvent(CartItemCount::class.java)
           cartItemCount?.let {
               if (isIncrease){
                   it.count+=1
                   EventBus.getDefault().postSticky(it)
               }else{
                   it.count-=1
                   EventBus.getDefault().postSticky(it)
               }
           }


            calculatePrice()
        }.ignoreElement()
    }
    private fun calculatePrice(){
           cartResponseLiveData.value?.let {cartResponseLiveData->
                totalPriceLiveData.value?.let {tp->
                    totalDiscountLiveData.value?.let { tD->
                        var totalPrice: Long=0
                        var totalDiscount=0F
                                cartResponseLiveData.message.forEach { cartItem ->
                                    totalPrice+=(cartItem.price.toLong()*cartItem.count.toLong())
                                    totalDiscount+=(cartItem.discount.toLong()*(cartItem.price.toLong()*cartItem.count.toInt()))/100
                                }


                        totalPriceLiveData.postValue(totalPrice)
                        totalDiscountLiveData.postValue(totalDiscount)

                    }

                }
            }

    }

      fun getCartList(){
       if (!TokenHolder.token.isNullOrEmpty()){
           progressBarLiveData.value=true
           emptyStateLiveData.value=EmptyState(false)
           iCartItemRpo.cartItemResponse()
               .doFinally { progressBarLiveData.postValue(false) }
               .singleHelper()
               .subscribe(object : MySingleObserver<CartResponse>(compositedisposable){
                   override fun onSuccess(t: CartResponse) {
                       if (t.message.isNotEmpty()){
                           emptyStateLiveData.value=EmptyState(false)
                           cartResponseLiveData.value=t
                           totalPriceLiveData.value=t.totalPrice
                           totalDiscountLiveData.value=t.totalDiscount.toFloat()
                       }else{
                           emptyStateLiveData.value= EmptyState(true,R.string.emptyState_emptyBasket)
                       }

                   }
               })
       }else{
           emptyStateLiveData.value= EmptyState(true,R.string.emptyState_signUp,true)
       }
    }
}