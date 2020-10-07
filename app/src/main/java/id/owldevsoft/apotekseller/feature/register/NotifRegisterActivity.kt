package id.owldevsoft.apotekseller.feature.register

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.owldevsoft.apotekseller.R
import id.owldevsoft.apotekseller.feature.login.LoginActivity
import kotlinx.android.synthetic.main.activity_notif_register.*

class NotifRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notif_register)

        btnOke.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}