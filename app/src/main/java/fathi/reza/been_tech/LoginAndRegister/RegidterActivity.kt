package fathi.reza.been_tech.LoginAndRegister

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import fathi.reza.been_tech.LoginAndRegister.viewModel.LoginViewModel
import fathi.reza.been_tech.MainActivity
import fathi.reza.been_tech.R
import org.koin.android.ext.android.inject

class RegidterActivity : AppCompatActivity() {
    private val loginViewModel:LoginViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regidter)

        val email=findViewById<EditText>(R.id.edt_register_email)
        val pass=findViewById<EditText>(R.id.edt_register_pass)
        val btnRegister=findViewById<Button>(R.id.btn_register_entreAccount)
        val enter=findViewById<TextView>(R.id.txt_register_enter)

        val extras=intent.extras
        if (extras != null) {
            val isShowButtonRegister=extras.getBoolean("isShowButtonRegister")
            if (!isShowButtonRegister) {
                enter.visibility=View.GONE
            }
        }

        enter.setOnClickListener {
            finish()
            startActivity(Intent(applicationContext,LoginActivity::class.java))

        }


        btnRegister.setOnClickListener{
            loginViewModel.register(email.text.toString(),pass.text.toString())
        }

        loginViewModel.registerMessage.observe(this) {
            if (it.status == "error") {
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_LONG).show()
            }else {
                Toast.makeText(applicationContext, "ثبت نام با موفقیت انجام شد", Toast.LENGTH_LONG).show()
                setResult(Activity.RESULT_OK)
                finish()

            }
        }

    }
}