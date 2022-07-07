package fathi.reza.been_tech.LoginAndRegister


import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import fathi.reza.been_tech.LoginAndRegister.viewModel.LoginViewModel
import fathi.reza.been_tech.Profile.BeeTechFragment
import fathi.reza.been_tech.R
import org.koin.android.ext.android.inject
import android.os.Build




class LoginActivity : AppCompatActivity() {
    private val loginViewModel:LoginViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val email=findViewById<EditText>(R.id.edt_login_email)
        val pass=findViewById<EditText>(R.id.edt_login_pass)
        val btnEnter=findViewById<Button>(R.id.btn_login_entreAccount)
        val register=findViewById<TextView>(R.id.txt_login_register)

        register.setOnClickListener {
            finish()
            startActivity(Intent(applicationContext,RegidterActivity::class.java))

        }

        btnEnter.setOnClickListener {
            loginViewModel.login(email.text.toString(),pass.text.toString())
        }
        loginViewModel.loginMessage.observe(this) {
            if (it.status == "success") {
                Toast.makeText(applicationContext, "ورود با موفقیت انجام شد", Toast.LENGTH_LONG).show()
                finish()

            } else {
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_LONG).show()
            }
        }


    }
}