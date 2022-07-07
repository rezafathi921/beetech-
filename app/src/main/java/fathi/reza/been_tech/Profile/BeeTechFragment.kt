package fathi.reza.been_tech.Profile


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import fathi.reza.been_tech.LoginAndRegister.RegidterActivity
import fathi.reza.been_tech.LoginAndRegister.viewModel.LoginViewModel
import fathi.reza.been_tech.R
import fathi.reza.been_tech.Utills.MyFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class BeeTechFragment : MyFragment() {
val loginViewModel:LoginViewModel by viewModel()

    var isLogin=MutableLiveData<Boolean>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        loginViewModel.loginMessage.removeObservers(this)
        loginViewModel.loginMessage.observe(this) {
            if (it.status == "success") {
                Toast.makeText(context, "ورود با موفقیت انجام شد", Toast.LENGTH_LONG).show()
                isLogin.value = true

            } else {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                isLogin.value = false
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       return inflater.inflate(R.layout.fragment_beetech, container, false)


        
    }
    var resultLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode==Activity.RESULT_OK){
            isLogin.value=true
            val beeTechFragment=BeeTechFragment()
            val fragmnetTranaction= (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
            fragmnetTranaction.detach(beeTechFragment)
            fragmnetTranaction.attach(beeTechFragment)
                .commit()
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val profileLogin=view.findViewById<ConstraintLayout>(R.id.profileLogin)
        val profileLogined=view.findViewById<NestedScrollView>(R.id.profileLogined)
        val email=view.findViewById<EditText>(R.id.edt_login_email)
        val pass=view.findViewById<EditText>(R.id.edt_login_pass)
        val btnEnter=view.findViewById<Button>(R.id.btn_login_entreAccount)
        val register=view.findViewById<TextView>(R.id.txt_login_register)



        loginViewModel.checkLogin()

        loginViewModel.checkLoginLivedataStatus.observe(viewLifecycleOwner){
            if (it) {
                profileLogined.visibility=View.VISIBLE
                profileLogin.visibility=View.GONE
            } else {
                profileLogined.visibility=View.GONE
                profileLogin.visibility=View.VISIBLE
            }
        }
        register.setOnClickListener {
            val intent=Intent(context, RegidterActivity::class.java)
            intent.putExtra("isShowButtonRegister",false)
            resultLauncher.launch(intent)

        }

        btnEnter.setOnClickListener {

            loginViewModel.login(email.text.toString(),pass.text.toString())
        }


        isLogin.observe(viewLifecycleOwner,{
            if (it) {
                profileLogined.visibility=View.VISIBLE
                profileLogin.visibility=View.GONE
            } else {

                profileLogined.visibility=View.GONE
                profileLogin.visibility=View.VISIBLE
            }
        })


    }



}