package fathi.reza.been_tech.CheckOut

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import fathi.reza.been_tech.CheckOut.viewmodel.CheckOutViewModel
import fathi.reza.been_tech.R
import fathi.reza.been_tech.Utills.MyActivity
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class CheckOut : MyActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_oute)
        val checkOutViewModel:CheckOutViewModel by inject {
            val uri:Uri?=intent.data
            if (uri!=null){
                val orderId= uri.getQueryParameters("order_id")
                parametersOf(orderId)
            }else{
                parametersOf(0)
            }

        }

        val txtOrderId=findViewById<TextView>(R.id.txt_checkOte_ordrId)
        val txtStateSale=findViewById<TextView>(R.id.txt_checkOut)
        checkOutViewModel.checkoutLiveData.observe(this) {
            if (it.status=="success"){
                txtStateSale.text="خرید با موفقیت انجام شد"
                txtOrderId.text=it.message
            }else{
                txtStateSale.text="خطا در تراکنش"
                txtOrderId.text=0.toString()
            }
        }
        checkOutViewModel.progressBarLiveData.observe(this){
            showProgressBar(it)
        }
    }
}