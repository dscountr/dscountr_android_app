package com.main.dscountr.view.user

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.main.dscountr.R
import com.main.dscountr.view.MainActivity
import kotlinx.android.synthetic.main.user_registration.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_registration)

        btn_sign_up.setOnClickListener {
            // Handler code here.
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btn_link_login.setOnClickListener {
            // Handler code here.
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}