package fathi.reza.been_tech.Retrofit

import fathi.reza.been_tech.Data.*
import fathi.reza.been_tech.Data.Cart.CartResponse
import fathi.reza.been_tech.Data.CatFragment.AllCat
import fathi.reza.been_tech.Data.Detail.Detail
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

import fathi.reza.been_tech.Data.Detail.CompareList
import fathi.reza.been_tech.Data.Detail.PriceHistory
import fathi.reza.been_tech.Data.LoginAndRegister.LoginMessage
import fathi.reza.been_tech.LoginAndRegister.TokenHolder
import okhttp3.OkHttpClient
import retrofit2.http.*
import java.util.concurrent.CountDownLatch


interface ApiService {
    @GET("getdetail.php")
    fun getDetail(@Query("id") id:String, @Query("user") user: String):Single<Detail>
    @GET("readbanner.php")
    fun getBannerSliders(): Single<List<Banners>>
    @GET("getcat.php")
    fun getCat():Single<List<Cats>>
    @GET("readamazing.php")
    fun getProuduct():Single<List<Products>>
    @GET("readitemdef.php")
    fun getItemDef():Single<List<DefItemProduct>>
    @GET("history.php")
    fun getPriceHistory(@Query("id") id :String):Single<List<PriceHistory>>
    @GET("getcomparelist.php")
    fun getcomparelist(@Query("cat") cat:String):Single<List<CompareList>>
    @FormUrlEncoded
    @POST("login2.php")
    fun login(@Field("email") email:String?,@Field("pass") pass:String?):Single<LoginMessage>
    @FormUrlEncoded
    @POST("register.php")
    fun register(@Field("email") email:String,@Field("pass") pass:String):Single<LoginMessage>
    @FormUrlEncoded
    @POST("checkToken.php")
    fun checkToken(@Field("token")token:String?,@Field("refresh_token")refresh_token:String?):Single<LoginMessage>
    @GET("deletefav2.php")
    fun deleteFromFav(@Query("id") id:String):Single<LoginMessage>
    @GET("addfavorite.php")
    fun addToFav(@Query("id") id:String):Single<LoginMessage>
    @GET("getallcatitem.php")
    fun getallcatitem():Single<AllCat>
    @GET("getbasket.php")
    fun cartItemResponse():Single<CartResponse>
    @GET("addbasket.php")
    fun addToBasket(@Query("productId")productId:String):Single<LoginMessage>
    @GET("changeCartItemCount.php")
    fun changeCartItemCart(@Query("productId") productId: String,@Query("count")count:Int):Single<LoginMessage>
    @GET("removeCartItem.php")
    fun removeCartItemFromBasket(@Query("productId") productId: String):Single<LoginMessage>
    @GET("getcartitemcount.php")
    fun getCartItemCount():Single<CartItemCount>
    @GET("checkout2.php")
    fun checkout(@Query("orderId")orderId:String):Single<LoginMessage>



}


fun getclient(): ApiService {

    val okHttpClient=OkHttpClient.Builder()
        .addInterceptor {
            val oldRequest=it.request()
            val newRequestBuilder=oldRequest.newBuilder()
            if (TokenHolder.token !=""){
                newRequestBuilder.addHeader("token",TokenHolder.token)
            }
            newRequestBuilder.addHeader("Accept","application/json")
            newRequestBuilder.method(oldRequest.method(),oldRequest.body())
                .build()
            return@addInterceptor it.proceed(newRequestBuilder.build())
        }.build()

    val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.149.189/beetech/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()
    return retrofit.create(ApiService::class.java)
}


