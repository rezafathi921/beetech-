package fathi.reza.been_tech.Utills

import android.content.Context
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fathi.reza.been_tech.Data.Detail.Child
import fathi.reza.been_tech.Data.Detail.ParentAndChild
import fathi.reza.been_tech.R
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.text.DecimalFormat


abstract class BaseViewModel : ViewModel() {
    val compositedisposable = CompositeDisposable()
    var progressBarLiveData= MutableLiveData<Boolean>()

    override fun onCleared() {
        super.onCleared()
        compositedisposable.clear()

    }

}
abstract class MyCompletableObserver(val compositeDisposable: CompositeDisposable):CompletableObserver{
    override fun onSubscribe(d: Disposable) {
        compositeDisposable.add(d)
    }

    override fun onError(e: Throwable) {
        Log.i("LOG", e.toString())
    }
}
abstract class MySingleObserver<T>(val compositeDisposable: CompositeDisposable):SingleObserver<T>{
    override fun onSubscribe(d: Disposable) {
        compositeDisposable.add(d)
    }

    override fun onError(e: Throwable) {
         Log.i("LOG", e.toString())
    }
}

interface MyView {

    val root:CoordinatorLayout?
    val myContex:Context?


   fun showProgressBar(show:Boolean){
       root?.let { rootView->
           myContex?.let { cntx->
           var  progressBarView=rootView.findViewById<View>(R.id.frameProgressBar)
               if(progressBarView==null && show){
                   progressBarView=LayoutInflater.from(myContex).inflate(R.layout.progress_bar,rootView,false)
                   rootView.addView(progressBarView)
               }

                progressBarView?.visibility=if (show)View.VISIBLE else View.GONE
           }
       }
   }
    fun showEmptyState(layout:Int):View?{
        root?.let { 
            myContex?.let { cnt ->
                var emptyState=it.findViewById<View>(R.id.emptyState)
                return if (emptyState==null){
                    emptyState=LayoutInflater.from(cnt).inflate(layout,it,false)
                    it.addView(emptyState)
                    emptyState
                }else{
                    emptyState
                }
            }
        }
        return null
    }
}

 abstract class MyActivity:AppCompatActivity(),MyView{

     override val root: CoordinatorLayout?
         get() {
             val parent=window.decorView.findViewById<ViewGroup>(android.R.id.content)
             if(parent !is CoordinatorLayout){
                 parent.children.forEach {
                     if (it is CoordinatorLayout) {

                         return it
                     }
                 }
             }else{
                 return parent
             }
             throw Exception("error")
         }

     override val myContex: Context?
         get() = this

}
 abstract class MyFragment: Fragment(),MyView{

     override val root: CoordinatorLayout?
         get() = view as CoordinatorLayout

     override val myContex: Context?
         get() = context

 }


fun <T> Single<T>.singleHelper():Single<T>{
    return subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}
fun Completable.completableHelper():Completable{
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}


fun currencyFormat(amount: String): String{
    val formatter = DecimalFormat("###,###,###,###")
    return formatter.format(amount.toDouble())
}
fun PersianToEnglish(persianStr: String): String {
    var result = ""
    var en = '0'
    for (ch in persianStr) {
        en = ch
        when (ch) {
            '0' -> en = '۰'
            '1' -> en = '۱'
            '2' -> en = '۲'
            '3' -> en = '۳'
            '4' -> en = '۴'
            '5' -> en = '۵'
            '6' -> en = '۶'
            '7' -> en = '۷'
            '8' -> en = '۸'
            '9' -> en = '۹'
        }
        result = "${result}$en"
    }
    return result
}


fun mixParentAndChild(array:ArrayList<ParentAndChild>?):ArrayList<Any>{
    val size_ArrylistParentAndChild= array?.size
    val array_Parent=ArrayList<String>()
    val array_Child= ArrayList<List<Child>>()
    val array_parent_mix_child=ArrayList<Any>()
    for (i in 0 until size_ArrylistParentAndChild!!){
        array_Parent.add(array[i].title)
        array_Child.add(array[i].child)
    }
    val size_array_parent=array_Parent.size
    for (j in 0 until size_array_parent ){
        array_parent_mix_child.add(array_Parent[j])
        array_parent_mix_child.add(array_Child[j])
    }

    val size_array_parent_mix_child=array_parent_mix_child.size
    val final_array=ArrayList<Any>()
    for (k in 0 until size_array_parent_mix_child){
        if (array_parent_mix_child[k] is String) {
            final_array.add(array_parent_mix_child[k])
        }else {
            val childs =array_parent_mix_child[k] as ArrayList<*>
            if (childs.size==0){
                final_array.add(childs)
            }else{
                for (child in childs){
                    final_array.add(child)
                }}

        }
    }
    return final_array
}
